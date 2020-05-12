package cn.winfxk.breast.form.more;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.breast.form.FormBase;

/**
 * 玩家点击了一个商品项目 准备购买
 * 
 * @Createdate 2020/05/12 10:54:30
 * @author Winfxk
 */
public class ClickItem extends FormBase {
	private String Key;

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
