package cn.winfxk.breast.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.item.enchantment.Enchantment;
import cn.winfxk.breast.Activate;

/**
 * @Createdate 2020/05/11 18:28:55
 * @author Winfxk
 */
public class EnchantList {
	private Activate ac;
	private int ID;
	private String Name;
	public static EnchantList enchant;
	private List<EnchantList> list = new ArrayList<>();

	/**
	 * 根据一个未知值获取名称
	 * 
	 * @param obj     未知数据
	 * @param match   是否粗略匹配
	 * @param Default 匹配失败将会返回的内容
	 * @return
	 */
	public String objToName(Object obj, boolean match, String Default) {
		Enchantment enchantment = objToEnchant(obj, match, null);
		return enchantment == null ? Default : getName(enchantment, Default);
	}

	/**
	 * 根据一个未知值获取ID
	 * 
	 * @param obj 未知的值
	 * @return
	 */
	public int objToID(Object obj) {
		return objToID(obj, true, 0);
	}

	/**
	 * 根据一个未知值获取ID
	 * 
	 * @param obj     未知的值
	 * @param match   是否粗匹配
	 * @param Default 匹配失败将会返回的内容
	 * @return
	 */
	public int objToID(Object obj, boolean match, int Default) {
		Enchantment enchantment = objToEnchant(obj, match, null);
		return enchantment == null ? Default : enchantment.getId();
	}

	/**
	 * 根据一个未知值获取一个Enchantment对象
	 * 
	 * @param obj 未知的值<包括ID、名称>
	 * @return
	 */
	public Enchantment objToEnchant(Object obj) {
		return objToEnchant(obj, true, null);
	}

	/**
	 * 根据一个未知值获取一个Enchantment对象
	 * 
	 * @param obj         未知的值<包括ID、名称>
	 * @param match       是否粗略匹配
	 * @param enchantment 若匹配失败将会返回的内容
	 * @return
	 */
	public Enchantment objToEnchant(Object obj, boolean match, Enchantment enchantment) {
		String string = Tool.objToString(obj);
		if (obj == null || string == null || string.isEmpty())
			return enchantment;
		if (obj instanceof Enchantment)
			return (Enchantment) obj;
		if (Tool.isInteger(string))
			return Enchantment.get(Tool.ObjToInt(string)) == null ? enchantment
					: Enchantment.get(Tool.ObjToInt(string));
		return getEnchant(string, match, enchantment);
	}

	/**
	 * 根据Enchantment ID获取对象
	 * 
	 * @param ID          ID
	 * @param enchantment 若匹配失败将会返回的内容
	 * @return
	 */
	public Enchantment getEnchant(int ID) {
		return getEnchant(ID, null);
	}

	/**
	 * 根据Enchantment ID获取对象
	 * 
	 * @param ID          ID
	 * @param enchantment 若匹配失败将会返回的内容
	 * @return
	 */
	public Enchantment getEnchant(int ID, Enchantment enchantment) {
		for (EnchantList list : this.list)
			if (list.ID == ID)
				return Enchantment.get(ID) == null ? enchantment : Enchantment.get(ID);
		return enchantment;
	}

	/**
	 * 根据Enchantment名称获取对象
	 * 
	 * @param Name Enchantment名称
	 * @return
	 */
	public Enchantment getEnchant(String Name) {
		return getEnchant(Name, true, null);
	}

	/**
	 * 根据Enchantment名称获取对象
	 * 
	 * @param Name        Enchantment名称
	 * @param match       是否粗略匹配
	 * @param enchantment 若匹配失败将会返回的内容
	 * @return
	 */
	public Enchantment getEnchant(String Name, boolean match, Enchantment enchantment) {
		for (EnchantList list : this.list)
			if (list.Name.equals(Name) || (match && list.Name.toLowerCase().equals(Name.toLowerCase())))
				return Enchantment.get(list.ID) == null ? enchantment : Enchantment.get(list.ID);
		return enchantment;
	}

	/**
	 * 根据Enchantment对象获取名称
	 * 
	 * @param enchantment Enchantment对象
	 * @param Default
	 * @return
	 */
	public String getName(Enchantment enchantment) {
		return getName(enchantment, null);
	}

	/**
	 * 根据Enchantment对象获取名称
	 * 
	 * @param enchantment Enchantment对象
	 * @param Default
	 * @return
	 */
	public String getName(Enchantment enchantment, Object Default) {
		return getName(enchantment.getId(), Default);
	}

	/**
	 * 根据Enchant ID获取Enchant名称
	 * 
	 * @param ID      EnchantID
	 * @param Default 匹配失败将会返回的内容
	 * @return
	 */
	public String getName(int ID) {
		return getName(ID, null);
	}

	/**
	 * 根据EnchantID获取Enchant名称
	 * 
	 * @param ID      EnchantID
	 * @param Default 匹配失败将会返回的内容
	 * @return
	 */
	public String getName(int ID, Object Default) {
		for (EnchantList list : this.list)
			if (ID == list.ID)
				return list.Name;
		return Default == null ? null : Tool.objToString(Default);
	}

	/**
	 * 根据Enchant获取ID
	 * 
	 * @param enchantment Enchant对象
	 * @return
	 */
	public int getID(Enchantment enchantment) {
		return enchantment.getId();
	}

	/**
	 * 根据Enchant名称获取ID
	 * 
	 * @param Name Enchant名称
	 * @return
	 */
	public int getID(String Name) {
		return getID(Name, true, 0);
	}

	/**
	 * 根据Enchant名称获取ID
	 * 
	 * @param Name    Enchant名称
	 * @param match   是否忽略大小写
	 * @param Default 匹配失败将会返回的内容
	 * @return
	 */
	public int getID(String Name, boolean match, Object Default) {
		for (EnchantList list : this.list)
			if (list.Name.equals(Name) || (match && list.Name.toLowerCase().equals(Name.toLowerCase())))
				return list.ID;
		return Tool.isInteger(Default) ? Tool.ObjToInt(Default) : 0;
	}

	/**
	 * 外部构造
	 * 
	 * @param activate
	 */
	public EnchantList(Activate activate) {
		ac = activate;
		enchant = this;
		reload();
	}

	private EnchantList(Object ID, Object Name) throws Exception {
		if (ID == null || Name == null)
			throw new Exception("The ID or name of Enchant cannot be null");
		if (!Tool.isInteger(ID))
			throw new Exception("Enchant ID can only be a pure integer greater than or equal to zero!");
		this.ID = Tool.ObjToInt(ID);
		this.Name = Tool.objToString(Name);
	}

	public int reload() {
		list = new ArrayList<>();
		Map<String, Object> map = ac.getEnchantListConfig().getAll();
		Map<String, Object> item;
		for (Object obj : map.values()) {
			item = obj != null && obj instanceof Map ? (HashMap<String, Object>) obj : new HashMap<>();
			if (item.size() <= 0)
				continue;
			try {
				list.add(new EnchantList(item.get("ID"), item.get("Name")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list.size();
	}

	public List<EnchantList> getAll() {
		return new ArrayList<>(list);
	}
}
