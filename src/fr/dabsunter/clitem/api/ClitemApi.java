package fr.dabsunter.clitem.api;

import fr.dabsunter.clitem.ClickAction;
import fr.dabsunter.clitem.ClickType;
import fr.dabsunter.clitem.ClickableItem;

public class ClitemApi {
	
	public static void registerClickListener(ClickableItem item, ClickType type, ClickListener listener) {
		ClickAction action = item.getClickAction(type);
		if(action == null) {
			action = new ClickAction(item);
			action.addListener(listener);
			item.addClickAction(type, action);
		}else{
			action.addListener(listener);
		}
	}
	
	public static void unregisterClickListener(ClickableItem item, ClickType type, ClickListener listener) {
		ClickAction action = item.getClickAction(type);
		if(action != null) {
			action.removeListener(listener);
		}
	}

}
