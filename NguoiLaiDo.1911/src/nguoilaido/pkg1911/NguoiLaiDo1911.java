
package nguoilaido.pkg1911;

import View.ManHinhGame;

public class NguoiLaiDo1911 {

    public static void main(String[] args) {
        try {
                new ManHinhGame().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    
}
