package cn.winfxk.breast.form.more.sett.ent;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.breast.form.FormBase;

/**
 * @Createdate 2020/05/12 11:57:04
 * @author Winfxk
 */
public class delItem extends FormBase {
	private String Key;

	public delItem(Player player, FormBase upForm, String Key) {
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
