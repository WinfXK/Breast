package cn.winfxk.breast.form.more.sett.nbtedit;

import java.util.Arrays;
import java.util.List;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.winfxk.breast.form.FormBase;
import cn.winfxk.breast.form.more.sett.nbtedit.more.EnchantEditOfaddItem;
import cn.winfxk.breast.tool.EnchantList;
import cn.winfxk.breast.tool.SimpleForm;

/**
 * 编辑物品的附魔
 * 
 * @Createdate 2020/05/13 11:20:20
 * @author Winfxk
 */
public class EnchantEdit extends FormBase {
	private int Index;
	private Item item;
	private EnchantList eList;
	private String[] KK = { "{Player}", "{Money}", "{ItemName}", "{ItemID}", "{ItemDamage}", "{ItemPatn}",
			"{EnchantName}", "{EnchantID}" };

	/**
	 * 编辑物品的附魔
	 * 
	 * @param player
	 * @param upForm
	 * @param Index  物品的位置
	 */
	public EnchantEdit(Player player, FormBase upForm, int Index) {
		super(player, upForm);
		this.Index = Index;
		setSon("NBTEditorOfEditItemOfEnchantEdit");
		item = player.getInventory().getContents().get(Index);
		setSon("NBTEditorOfEditItem");
		setK("{Player}", "{Money}", "{ItemName}", "{ItemID}", "{ItemDamage}", "{ItemPatn}");
		setD(player.getName(), myPlayer.getMoney(), itemList.getName(item), item.getId(), item.getDamage(),
				itemList.getPath(item));
		eList = ac.getEnchants();
	}

	@Override
	public boolean MakeMain() {
		if (!myPlayer.isAdmin()) {
			player.sendMessage(getString("权限不足"));
			return isBack();
		}
		SimpleForm form = new SimpleForm(getID(), getTitle(), getContent());
		for (Enchantment enchant : item.getEnchantments()) {
			form.addButton(getString("EnchantItem", KK,
					new Object[] { player.getName(), myPlayer.getMoney(), itemList.getName(item), item.getId(),
							item.getDamage(), itemList.getPath(item), eList.getName(enchant), enchant.getId() }));
		}
		form.addButton(getString("addEnchant"));
		form.addButton(getBack());
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		int ID = getSimple(data).getClickedButtonId();
		if (item.getEnchantments().length < ID) {
			Enchantment enchantment = item.getEnchantments()[ID];
			List<Enchantment> list = Arrays.asList(item.getEnchantments());
			list.remove(enchantment);
			item.addEnchantment((Enchantment[]) list.toArray());
			player.getInventory().setItem(Index, item);
			player.sendMessage(getString("delOK", KK,
					new Object[] { player.getName(), myPlayer.getMoney(), itemList.getName(item), item.getId(),
							item.getDamage(), itemList.getPath(item), eList.getName(enchantment),
							enchantment.getId() }));
			return isBack();
		}
		if (item.getEnchantments().length == ID)
			return setForm(new EnchantEditOfaddItem(player, this, Index)).make();
		return isBack();
	}
}
