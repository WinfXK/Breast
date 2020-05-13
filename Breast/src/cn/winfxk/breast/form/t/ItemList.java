package cn.winfxk.breast.form.t;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.breast.form.FormBase;

/**
 * @Createdate 2020/05/13 17:06:17
 * @author Winfxk
 */
public class ItemList extends FormBase {

	public ItemList(Player player, FormBase upForm) {
		super(player, upForm);
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
