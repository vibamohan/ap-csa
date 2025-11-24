import java.util.HashMap;
import java.util.Map;

public class KeyStateManager {
  private final Map<Integer, Boolean> keyStates = new HashMap<>();

  public void setKeyPressed(int key, boolean pressed) {
    keyStates.put(key, pressed);
  }

  public boolean isPressed(int key) {
    return keyStates.getOrDefault(key, false);
  }

  public Map<Integer, Boolean> getAllKeyStates() {
    return new HashMap<>(keyStates);
  }
}
