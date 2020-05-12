package cn.winfxk.breast.form.more.sett;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.breast.form.FormBase;

/**
 * 显示当前服务器已经支持的物品
 * 
 * @Createdate 2020/05/12 11:31:36
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
