
package ridersofwekmar;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;

public class HotkeyHandler {
    
    mySidePanel sidePanel;
    
    public HotkeyHandler(mySidePanel sp)
    {
        sidePanel = sp;
        sidePanel.getGridPane().getParent().addEventHandler(KeyEvent.KEY_RELEASED, undoRedo);
    }
    
    KeyCombination ctrlZ = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
    KeyCombination ctrlY = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN);
    
    EventHandler<KeyEvent> undoRedo = (KeyEvent e) ->
    {
        if (ctrlZ.match(e))
        {
            sidePanel.fireUndoBtn();
        } else if (ctrlY.match(e))
        {
            sidePanel.fireRedoBtn();
        }
    };  

}
