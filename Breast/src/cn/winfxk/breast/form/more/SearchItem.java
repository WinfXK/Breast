package cn.winfxk.breast.form.more;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseCustom;
import cn.winfxk.breast.form.FormBase;
import cn.winfxk.breast.tool.CustomForm;

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
		CustomForm form = new CustomForm(getID(), getTitle());
		form.addLabel(getContent());
		form.addInput(getString("InputKeyword"), "", getString("InputKeyword"));
		form.addToggle(getString("SearchName"), true);
		form.addToggle(getString("SearchID"), true);
		form.addToggle(getString("SearchDamage"), true);
		form.addToggle(getString("SearchLore"), false);
		form.addToggle(getString("SearchEnchant"), false);
		form.sendPlayer(player);
		return true;
	}

	@Override
	public boolean disMain(FormResponse data) {
		FormResponseCustom d = getCustom(data);
		String Keyword = d.getInputResponse(1);
		if (Keyword == null || Keyword.isEmpty()) {
			player.sendMessage(getString("notKeyword"));
			return isBack();
		}
		return setForm(new SearchList(player, this, Keyword, d.getToggleResponse(2), d.getToggleResponse(3),
				d.getToggleResponse(4), d.getToggleResponse(5), d.getToggleResponse(6))).make();
	}
}