package cn.winfxk.breast;

import java.util.ArrayList;
import java.util.List;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.form.response.FormResponseSimple;

/**
 * 基础UI操作类
 *
 * @author Winfxk
 */
public abstract class FormBase implements Cloneable {
	protected Player player;
	protected Message msg;
	protected Activate ac;
	private FormBase make;
	protected FormID formID;
	protected String Son, Name, t;
	protected FormBase upForm;
	protected Object[] D = {};
	protected String[] K = {};
	protected MyPlayer myPlayer;
	protected List<String> listKey = new ArrayList<>();
	protected static final String Title = "Title", Content = "Content", Close = "Close", Back = "Back";

	/**
	 * 界面交互基础类
	 *
	 * @param player 操作界面的玩家对象
	 */
	public FormBase(Player player, FormBase upForm) {
		this.player = player;
		ac = Activate.getActivate();
		msg = ac.getMessage();
		formID = ac.getFormID();
		myPlayer = ac.getPlayers(player.getName());
		Son = getClass().getSimpleName();
		Name = getClass().getSimpleName();
		try {
			this.upForm = upForm.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			this.upForm = null;
		}
	}

	/**
	 * 设置主分类
	 * 
	 * @param string
	 */
	public void setT(String string) {
		t = string;
	}

	/**
	 * 返回页面的不重复ID</br>
	 * <b>PS: </b> 我自己懂这个是啥意思就好了你瞎掺和啥
	 *
	 * @return 不重复的ID
	 */
	public int getID() {
		int i = 0;
		switch (myPlayer.ID) {
		case 0:
			i = 1;
			break;
		case 1:
			i = 2;
			break;
		case 2:
			i = 0;
			break;
		}
		myPlayer.ID = i;
		return formID.getID(myPlayer.ID);
	}

	/**
	 * 返回初始化的数据
	 *
	 * @return 返回Msg数据
	 */
	public Object[] getD() {
		return D;
	}

	/**
	 * 返回初始化的表
	 *
	 * @return 返回Msg键
	 */
	public String[] getK() {
		return K;
	}

	/**
	 * 页面主页
	 *
	 * @return 构建是否成功
	 */
	public abstract boolean MakeMain();

	/**
	 * 页面返回的数据
	 *
	 * @param data 界面传递的数据
	 * @return 数据处理是否成功
	 */
	public abstract boolean disMain(FormResponse data);

	/**
	 * 将书强转多样型
	 *
	 * @param data 默认的数据
	 * @return 自定义数据
	 */
	public FormResponseCustom getCustom(FormResponse data) {
		return (FormResponseCustom) data;
	}

	/**
	 * 将数据强转简单型
	 *
	 * @param data 默认的数据
	 * @return 简单截面数据
	 */
	public FormResponseSimple getSimple(FormResponse data) {
		return (FormResponseSimple) data;
	}

	/**
	 * 将数据强转选择型
	 *
	 * @param data 默认的数据
	 * @return 选择型界面的数据
	 */
	public FormResponseModal getModal(FormResponse data) {
		return (FormResponseModal) data;
	}

	/**
	 * 设置数据
	 *
	 * @param objects 要设置的Msg数据
	 */
	public void setD(Object... objects) {
		D = objects;
	}

	/**
	 * 设置表
	 *
	 * @param strings 要设置的Msg键
	 */
	public void setK(String... strings) {
		K = strings;
	}

	/**
	 * 设置一个页面为当前玩家操作的页面
	 *
	 * @param base 即将给玩家显示的界面对象
	 * @return 当前操作的界面
	 */
	public FormBase setForm(FormBase base) {
		make = base;
		return this;
	}

	/**
	 * 构建下一个界面
	 *
	 * @return 下一个构建是否成功
	 */
	public boolean make() {
		if (make == null)
			throw new FormException("The interface is empty, unable to display normally! Please contact Winfxk.");
		return (myPlayer.form = make).MakeMain();
	}

	@Override
	public String toString() {
		return player.getName() + " interface(" + getID() + "," + Name + ")";
	}

	/**
	 * 返回这个界面的名字
	 * 
	 * @return
	 */
	public String getName() {
		return Name;
	}

	/**
	 * 设置界面名称
	 * 
	 * @param name
	 */
	public void setName(String name) {
		Name = name;
	}

	@Override
	public FormBase clone() throws CloneNotSupportedException {
		FormBase base = (FormBase) super.clone();
		if (base == null)
			throw new FormException("The cloned object is null.");
		if (upForm != null)
			base.upForm = upForm.clone();
		base.listKey = new ArrayList<>();
		base.make = null;
		return base;
	}
}
