package main.NewGuis;



import main.Actions.Action;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Created by SRH on 1/9/2018.
 */
public class ActionGuiInfo extends NewStatementGUI {
    public ActionGuiInfo(ArrayList<Action> actionList, Consumer<Integer> updateTextfield) {
        String[] actionTypes = new String[]{"Interact with entity", "Take Ground item", "Inventory item interact", "Use item on", "Type", "Click xy", "Sleep", "Send raw Action", "Walk to"};
        NewStatementGUI.Descriptions[] setDescs = {
                new Descriptions("Entity to interact with (eg. \"1767,1768\" selects cows)", "Option to select (eg. \"1\") (Must be filled)"),
                new Descriptions("Item to take (eg. 526 picks up bones)"),
                new Descriptions("Item in your inventory to use (eg. 951 = \"Silk\")", "Option to select (eg. 1 might be \"eat\")"),
                new Descriptions("Item in your inventory to use (eg. 999 = \"Bones\")", "Entity to use it on (eg. 999 = altar)<999 isn't actually altar>", "Item option to select (i.e. \"Use\") (cAsE sEnSiTiVe)"),
                new Descriptions("Text to type in (eg. 28)", "Hit enter? (0 for no, 1 for yes)"),
                new Descriptions("X coordinate to click (eg. 0)", "Y coordinate to click (eg. 600)", "Click type? (0 for left, 1 for right)"),
                new Descriptions("Amount of time to sleep (ms)"),
                new Descriptions("Action id", "Actions in format: \"action1; action2; action3; action4\""),
                new Descriptions("X-tile coordinate", "Y-tile coordinate"),
        };

        initGui("Add new action", actionList, updateTextfield, actionTypes, setDescs);
    }
}
