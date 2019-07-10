package main.java.Strategies;

import main.java.Actions.Action;
import main.java.Actions.ActionHandler;
import main.java.Actions.Logic.Endif;
import main.java.Actions.Logic.If;
import main.java.Actions.Logic.IfNot;
import main.java.Actions.Logic.LogicHandler;
import main.java.Actions.SubscriptHandler;
import main.java.VarsMethods;
import org.parabot.environment.api.utils.Time;

import java.util.ArrayList;
import java.util.Stack;

import static main.java.VarsMethods.log;

public class ActionExecutor {
    private ArrayList<Action> actions;
    private ActionHandler actionHandler;
    private LogicHandler logicHandler;
    private Stack ifStack;

    private int lineIndex;

    public ActionExecutor(ArrayList<Action> actions) {
        this.actions = actions;

        actionHandler = new ActionHandler();
        logicHandler = new LogicHandler();
        ifStack = new Stack();
        ifStack.push("True");

        lineIndex = 0;
    }

    public void execute()
    {
        Action line = actions.get(lineIndex);
        lineIndex = ++lineIndex == actions.size() ? 0 : lineIndex;

        if (line instanceof Endif)
        {
            ifStack.pop();
            return;
        } else if ((line instanceof If || line instanceof IfNot) && ifStack.peek().equals("False")) {
            ifStack.push("False");
            return;
        }

        if (ifStack.peek().equals("True"))
        {
            VarsMethods.currentAction = line.getAction();

            try {
                executeLine(line);
            } catch (NumberFormatException notFilledIn) {
                log("Error on line " + line);
                log("Make sure you fill in all numeric values properly! Numbers only!");
            }

            Time.sleep(VarsMethods.tickSpeed);
        }

        Time.sleep(50);
    }

    private void executeLine(Action action) {
        if (action instanceof If)
        {
            ifStack.push(logicHandler.determineIfAsBoolString(action));
        }
        else if (action instanceof IfNot)
        {
            ifStack.push(logicHandler.determineIfNotAsBoolString(action));
        }
        else
        {
            switch (action.getMethod().replace("-", " "))
            {
                case "Interact with entity by ID":
                    actionHandler.handleInteractWith(action);
                    break;
                case "Interact with entity by location":
                    actionHandler.handleInteractWithByLoc(action);
                    break;
                case "Take Ground item":
                    actionHandler.handleGroundItemInteract(action);
                    break;
                case "Inventory item interact":
                    actionHandler.inventoryItemInteract(action);
                    break;
                case "Use item on":
                    actionHandler.useItemOn(action);
                    break;
                case "Type":
                    actionHandler.type(action);
                    break;
                case "Click xy":
                    actionHandler.clickxy(action);
                    break;
                case "Sleep":
                    actionHandler.sleep(action);
                    break;
                case "Send raw Action":
                    actionHandler.sendRawAction(action);
                    break;
                case "Walk to":
                    actionHandler.walkTo(action);
                    break;
                case "Run subscript":
                    SubscriptHandler.runSubscript(action.getParamAsString(0));
                    break;
                case "Bank all except IDs":
                    actionHandler.bankAllExcept(action);
                    break;
                default:
                    log("Error: Unimplemented action: " + action.getAction());
            }
        }
    }
}