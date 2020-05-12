package cn.winfxk.breast.form.more;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.breast.form.FormBase;

/**
 * 删除商店项目页面
 * 
 * @Createdate 2020/05/12 11:05:40
 * @author Winfxk
 */
public class DeleteItem extends FormBase {

	public DeleteItem(Player player, FormBase upForm) {
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
