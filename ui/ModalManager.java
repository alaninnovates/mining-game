package ui;

public class ModalManager {
    private boolean modalOpen;
    private static ModalManager instance;

    private ModalManager() {
        modalOpen = false;
    }

    public static ModalManager getInstance() {
        if (instance == null) {
            instance = new ModalManager();
        }
        return instance;
    }

    public boolean isAnyModalOpen() {
        return modalOpen;
    }

    public void setAnyModalOpen(boolean modalOpen) {
        this.modalOpen = modalOpen;
    }
}
