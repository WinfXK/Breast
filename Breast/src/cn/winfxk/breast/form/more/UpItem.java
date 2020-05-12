package cn.winfxk.breast.form.more;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.breast.form.FormBase;

/**
 * 上架物品页面
 * 
 * @Createdate 2020/05/12 11:06:10
 * @author Winfxk
 */
public class UpItem extends FormBase {

	public UpItem(Player player, FormBase upForm) {
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
