package cn.winfxk.breast.form.more;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.breast.form.FormBase;

/**
 * 搜索物品页面
 * 
 * @Createdate 2020/05/12 11:05:19
 * @author Winfxk
 */
public class SearchItem extends FormBase {

	public SearchItem(Player player, FormBase upForm) {
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
