package cn.winfxk.breast.form.more.sett.ent;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.breast.form.FormBase;

/**
 * 玩家点击了附魔项目
 * 
 * @Createdate 2020/05/12 11:55:53
 * @author Winfxk
 */
public class ClickItem extends FormBase {
	private String Key;

	/**
	 * 玩家点击了附魔项目
	 * 
	 * @param player
	 * @param upForm
	 * @param Key
	 */
	public ClickItem(Player player, FormBase upForm, String Key) {
		super(player, upForm);
		this.Key = Key;
	}

	@Override
	public boolean MakeMain() {
		return false;
	}

	@Override
	public boolean disMain(FormResponse data) {
		return false;
	}
}
