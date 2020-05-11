package cn.winfxk.breast.form;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.breast.tool.SimpleForm;

/**
 * 主页
 * 
 * @Createdate 2020/05/09 17:35:43
 * @author Winfxk
 */
public class MainForm extends FormBase {

	public MainForm(Player player, FormBase upForm) {
		super(player, upForm);
	}

	@Override
	public boolean MakeMain() {
		SimpleForm form = new SimpleForm(getID(), Title, Content);

		return false;
	}

	@Override
	public boolean disMain(FormResponse data) {
		return false;
	}
}
