/*
 * 
 */
package net.classic.remastered.client.settings;

public enum ThirdPersonMode {
    NONE,
    BACK_FACING,
    FRONT_FACING{

        @Override
        public ThirdPersonMode next() {
			return null;
        }
    };


    private ThirdPersonMode() {
    }

    public ThirdPersonMode next() {
        return ThirdPersonMode.values()[this.ordinal() + 1];
    }

    /* synthetic */ ThirdPersonMode(String string, int n, ThirdPersonMode thirdPersonMode) {
        this();
    }
}

