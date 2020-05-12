package cn.winfxk.breast.form.more.sett.ent;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.breast.form.FormBase;

/**
 * 新建附魔文档
 * 
 * @Createdate 2020/05/12 11:56:32
 * @author Winfxk
 */
public class addItem extends FormBase {

	public addItem(Player player, FormBase upForm) {
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
