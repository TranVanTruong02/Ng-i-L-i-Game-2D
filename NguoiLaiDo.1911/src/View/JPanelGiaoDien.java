
package View;

import Model.CartoonModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

public class JPanelGiaoDien extends javax.swing.JPanel {
    
    private CartoonModel soi;
    private CartoonModel de;
    private CartoonModel bapcai;
    private CartoonModel thuyen;
    private CartoonModel nguoilaido;
    
    private Animator animator;
    private Animator animatorDe;
    private Animator animatorBapcai;
    
    private Point animatePointNew;

    // Xây dựng danh sách trạng thái con vật trên bờ 1 (khởi tạo tất cả con vật đều trên bờ 1 là true)
    private boolean buttonSoiBo1 = true;
    private boolean buttonDeBo1 = true;
    private boolean buttonBapcaiBo1 = true;
    
    // Xây dựng danh sách trạng thái con vật trên bờ 2 (khởi tạo không có con vật nào trên bờ 2 là false)
    private boolean buttonSoiBo2 = false;
    private boolean buttonDeBo2 = false;
    private boolean buttonBapcaiBo2 = false;
    
    // Trạng thái bờ 1 và bờ 2
    private boolean benbo1 = true; // Thuyền đang ở bến
    private boolean  benbo2 = false; // Thuyền chưa ở bến
    
    // Xét trạng thái con vật có trên thuyền hay không
    private boolean trenthuyen = false;
    
    // Thực hiện animation lần 2 đưa các con vật lên bờ
    private boolean animation2 = false;
    
    // Kiểm tra điều kiện ăn thịt nhau của các con vậ
    private boolean endsoiande = false;
    private boolean enddeanbapcai = false;
    
    // Tắt repain khi mở messenger
    private boolean messenger = false;
    
    // load lại dữ liệu
    private boolean reload = false;
    
    // Kiểm tra xem luật chơi
    private boolean luatchoi = false;
    
    public JPanelGiaoDien() {
        initComponents();
        
        GetData();
        
        // Mở đầu
        JPMoDau.setVisible(true);
        JPLuatChoi.setVisible(false);
        JPLuatChoi.setBackground(new Color(100, 100, 100, 150));
        messenger = true;
        
        // Ảnh các sự kiện ăn nhau
        jbo1soiande.setVisible(false);
        jbo1deanbapcai.setVisible(false);
        jbo2soiande.setVisible(false);
        jbo2deanbapcai.setVisible(false);

        // Messenger của nhân vật
        jPmessenger.setBackground(new Color(0, 0, 0, 0));
        jPmessenger.setVisible(false);
        jPMessangeCartoon.setVisible(false);
        jPMessangeCartoon.setBackground(new Color(0, 0, 0, 0));
        jPMessangeCartoon1.setVisible(false);
        jPMessangeCartoon1.setBackground(new Color(0, 0, 0, 0));
       
        // Thông báo mất tiến trình
        JPTienTrinh.setVisible(false);
        JPTienTrinh.setBackground(new Color(100, 100, 100, 150));
        
        // Thông báo thắng và thua
        JPThuaCuoc.setVisible(false);
        JPThuaCuoc.setBackground(new Color(100, 100, 100, 150));
        JPChienThang.setVisible(false);
        JPChienThang.setBackground(new Color(100, 100, 100, 150));
        
        // Màn hình giới thiệu và hướng dẫn
        JPGioiThieu.setVisible(false);
        JPGioiThieu.setBackground(new Color(100, 100, 100, 150));
        JPHuongDan.setVisible(false);
        JPHuongDan.setBackground(new Color(100, 100, 100, 150));
    }
    
    private void Reload(){
        // Đưa về trạng thái ban đầu
        buttonSoiBo1 = true;
        buttonDeBo1 = true;
        buttonBapcaiBo1 = true;

        buttonSoiBo2 = false;
        buttonDeBo2 = false;
        buttonBapcaiBo2 = false;

        benbo1 = true; 
        benbo2 = false; 
        trenthuyen = false;
        animation2 = false;
        endsoiande = false;
        enddeanbapcai = false;
        // Load lại dữ liệu
        GetData();
        reload = false;
        // Mở button
        ExamButton();
    }
    
    private void GetData(){
       soi = new CartoonModel(new ImageIcon(getClass().getResource("/Image/wolf.png")), new Dimension(130, 110), new Point(250, 370)); // Sói
       de = new CartoonModel(new ImageIcon(getClass().getResource("/Image/goat.png")), new Dimension(120, 120), new Point(250, 260)); // Dê
       bapcai = new CartoonModel(new ImageIcon(getClass().getResource("/Image/cabbage.png")), new Dimension(70, 70), new Point(370, 220)); // Bắp cải
       thuyen = new CartoonModel(new ImageIcon(getClass().getResource("/Image/boat.png")), new Dimension(260, 170), new Point(400, 323)); // Thuyền
       nguoilaido = new CartoonModel(new ImageIcon(getClass().getResource("/Image/farmer.png")), new Dimension(110, 140), new Point(530, 280)); // Bác lái thuyền

    }
    
    // Vẽ
    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);   
        Rectangle size, size1, size2, size3, size4;
        if(messenger == false){
            size = getAutoSize(soi.getImage(), soi.getImageSize());
            g2.drawImage(toImage(soi.getImage()), soi.getImageLocation().x, soi.getImageLocation().y, size.getSize().width, size.getSize().height, null);
            size1 = getAutoSize(de.getImage(), de.getImageSize());
            g2.drawImage(toImage(de.getImage()), de.getImageLocation().x, de.getImageLocation().y, size1.getSize().width, size1.getSize().height, null);
            size2 = getAutoSize(bapcai.getImage(), bapcai.getImageSize());
            g2.drawImage(toImage(bapcai.getImage()), bapcai.getImageLocation().x, bapcai.getImageLocation().y, size2.getSize().width, size2.getSize().height, null);
            size3 = getAutoSize(thuyen.getImage(), thuyen.getImageSize());
            g2.drawImage(toImage(thuyen.getImage()), thuyen.getImageLocation().x, thuyen.getImageLocation().y, size3.getSize().width, size3.getSize().height, null); 
            size4 = getAutoSize(nguoilaido.getImage(), nguoilaido.getImageSize());
            g2.drawImage(toImage(nguoilaido.getImage()), nguoilaido.getImageLocation().x, nguoilaido.getImageLocation().y, size4.getSize().width, size4.getSize().height, null);
        }
        g2.dispose();
    }

    private Rectangle getAutoSize(Icon image, Dimension size) {
        int w = size.width;
        int h = size.height;
        if (w > image.getIconWidth()) {
            w = image.getIconWidth();
        }
        if (h > image.getIconHeight()) {
            h = image.getIconHeight();
        }
        int iw = image.getIconWidth();
        int ih = image.getIconHeight();
        double xScale = (double) w / iw;
        double yScale = (double) h / ih;
        double scale = Math.max(xScale, yScale);
        int width = (int) (scale * iw);
        int height = (int) (scale * ih);
        int x = getWidth() / 2 - (width / 2);
        int y = getHeight() / 2 - (height / 2);
        return new Rectangle(new Point(x, y), new Dimension(width, height));
    }

    private Image toImage(Icon icon) {
        return ((ImageIcon) icon).getImage();
    }
    
    private void ViewAnimation(int i, Point newPoint){
        if(i == 1){ // Sói
            animator = PropertySetter.createAnimator(1000, soi, "imageLocation", soi.getImageLocation(), newPoint);
        }
        else if(i == 2){ // Dê
            animator = PropertySetter.createAnimator(1000, de, "imageLocation", de.getImageLocation(), newPoint);
        }
        else if(i == 3){ // Bắp cải
            animator = PropertySetter.createAnimator(1000, bapcai, "imageLocation", bapcai.getImageLocation(), newPoint);
        }
        else if(i == 4){ // Thuyền
            animator = PropertySetter.createAnimator(1000, thuyen, "imageLocation", thuyen.getImageLocation(), newPoint);
        }
        else if(i == 5){ // Bác lái đò
            animator = PropertySetter.createAnimator(1000, nguoilaido, "imageLocation", nguoilaido.getImageLocation(), newPoint);
        }
        animator.addTarget(new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                repaint();
            }

            @Override
            public void end() {
                // Bờ 1
                if(enddeanbapcai == true && buttonDeBo1 == true && buttonBapcaiBo1 == true && buttonDeBo2 == false && buttonBapcaiBo2 == false){
                    messenger = true;
                    repaint();
                    jbo1deanbapcai.setVisible(true);
                    // Thông báo thua cuộc
                    JPThuaCuoc.setVisible(true);
                    jthongbaothuacuoc.setText("Dê ăn Bắp Cải rồi kìa");
                }
                if(endsoiande == true && buttonSoiBo1 == true && buttonDeBo1 == true && buttonSoiBo2 == false && buttonDeBo2 == false){
                    messenger = true;
                    repaint();
                    jbo1soiande.setVisible(true);
                    // Thông báo thua cuộc
                    JPThuaCuoc.setVisible(true);
                    jthongbaothuacuoc.setText("Sói ăn Dê rồi kìa...");
                }
                
                //Bờ 2
                if(enddeanbapcai == true && buttonDeBo2 == true && buttonBapcaiBo2 == true && buttonDeBo1 == false && buttonBapcaiBo1 == false){
                    messenger = true;
                    repaint();
                    jbo2deanbapcai.setVisible(true);
                    // Thông báo thua cuộc
                    JPThuaCuoc.setVisible(true);
                    jthongbaothuacuoc.setText("Dê ăn Bắp Cải rồi kìa");
                }
                if(endsoiande == true && buttonSoiBo2 == true && buttonDeBo2 == true && buttonSoiBo1 == false && buttonDeBo1 == false){
                    messenger = true;
                    repaint();
                    jbo2soiande.setVisible(true);
                    // Thông báo thua cuộc
                    JPThuaCuoc.setVisible(true);
                    jthongbaothuacuoc.setText("Sói ăn Dê rồi kìa...");
                }
                
                // Luật chơi
                if(luatchoi == true){
                    if(buttonSoiBo1 == true && buttonDeBo1 == true && buttonBapcaiBo1 == true && trenthuyen == false){
                        jPMessangeCartoon.setVisible(true);
                    }
                }
                
                if(animation2 == true){
                    // Kiểm tra đi thuyền từ bờ 1 sang 2
                    if(benbo1 == false && benbo2 == true){
                        if(FunctionEvaluation(buttonSoiBo1, buttonDeBo1, buttonBapcaiBo1, benbo1) == true){
                            if(buttonSoiBo1 == false && trenthuyen == true && buttonSoiBo2 == false){ // Nếu sói ở bờ 1 trên thuyền
                                // Đưa sói qua bờ
                                animatePointNew = new Point(1010, 380);
                                ViewAnimation(1, animatePointNew);
                                soi.setImageLocation(animatePointNew);
                                animation2 = false;
                                trenthuyen = false;
                                // Xét lại vị trí trên bờ
                                buttonSoiBo2 = true;
                                ExamButton();
                            }
                            else if(buttonDeBo1 == false && trenthuyen == true && buttonDeBo2 == false){ // Nếu dê trên thuyền
                                // Xét lại vị trí trên bờ
                                buttonDeBo2 = true;
                                // Đưa dê qua bờ
                                animatePointNew = new Point (970, 290);
                                
                                // Nếu đã qua hết thì thông báo thằng cuộc
                                if(buttonSoiBo2 == true && buttonDeBo2 == true && buttonBapcaiBo2 == true){ 
                                    ViewAnimationChienThang(animatePointNew);
                                }
                                else{
                                    ViewAnimation(2, animatePointNew);
                                }
                                de.setImageLocation(animatePointNew);
                                animation2 = false;
                                trenthuyen = false;
                                ExamButton();
                            }
                            else if(buttonBapcaiBo1 == false && trenthuyen == true && buttonBapcaiBo2 == false){ // Nếu bắp cải trên thuyền
                                // Đưa bắp cải qua bờ
                                animatePointNew = new Point (980, 220);
                                ViewAnimation(3, animatePointNew);
                                bapcai.setImageLocation(animatePointNew);
                                animation2 = false;
                                trenthuyen = false;
                                // Xét lại vị trí trên bờ
                                buttonBapcaiBo2 = true;
                                ExamButton();
                            }
                        }
                    }
                    else if(benbo1 == true && benbo2 == false){
                        if(FunctionEvaluation(buttonSoiBo2, buttonDeBo2, buttonBapcaiBo2, benbo2) == true){
                            if(buttonSoiBo2 == false && trenthuyen == true && buttonSoiBo1 == false){ // Nếu sói ở bờ 1 trên thuyền
                                // Đưa sói qua bờ
                                animatePointNew = new Point(250, 370);
                                ViewAnimation(1, animatePointNew);
                                soi.setImageLocation(animatePointNew);
                                animation2 = false;
                                trenthuyen = false;
                                // Xét lại vị trí trên bờ
                                buttonSoiBo1 = true;
                                ExamButton();
                            }
                            else if(buttonDeBo2 == false && trenthuyen == true && buttonDeBo1 == false){ // Nếu dê trên thuyền
                                // Xét lại vị trí trên bờ
                                buttonDeBo1 = true;
                                // Đưa dê qua bờ
                                animatePointNew = new Point (250, 260);
                                
                                // Nếu đã qua hết thì thông báo thằng cuộc
                                if(buttonSoiBo2 == true && buttonDeBo2 == true && buttonBapcaiBo2 == true){ 
                                    ViewAnimationChienThang(animatePointNew);
                                }
                                else{
                                    ViewAnimation(2, animatePointNew);
                                }
                                de.setImageLocation(animatePointNew);
                                animation2 = false;
                                trenthuyen = false;
                                ExamButton();
                            }
                            else if(buttonBapcaiBo2 == false && trenthuyen == true && buttonBapcaiBo1 == false){ // Nếu bắp cải trên thuyền
                                // Đưa bắp cải qua bờ
                                animatePointNew = new Point (370, 220);
                                ViewAnimation(3, animatePointNew);
                                bapcai.setImageLocation(animatePointNew);
                                animation2 = false;
                                trenthuyen = false;
                                // Xét lại vị trí trên bờ
                                buttonBapcaiBo1 = true;
                                ExamButton();
                            }
                        }
                    }
                }
            }
        });
        animator.setResolution(5);
        animator.start();
    }  
    
    private void ViewAnimationChienThang(Point newPoint){
        // Dê
        animator = PropertySetter.createAnimator(1000, de, "imageLocation", de.getImageLocation(), newPoint);
        animator.addTarget(new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                repaint();
                jthongbao.setText("Bạn đã chiến thắng. Bạn thật giỏi");
                jPMessangeCartoon1.setVisible(true);
            }

            @Override
            public void end() {
                try {
                    // Thông báo thắng cuộc
                    Thread.sleep(1000);
                    messenger = true;
                    repaint();
                    JPChienThang.setVisible(true);
                } catch (InterruptedException ex) {
                    Logger.getLogger(JPanelGiaoDien.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        animator.setResolution(5);
        animator.start();
    }  
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JPMoDau = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        buttonMoDau = new Ultil.Button();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        JPTienTrinh = new javax.swing.JPanel();
        backgroundDialog2 = new Ultil.BackgroundDialog();
        btTienTrinhHuy = new Ultil.Button();
        btTienTrinhTiepTuc = new Ultil.Button();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jthongbaothuacuoc1 = new javax.swing.JLabel();
        JPLuatChoi = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        btLuatChoi = new Ultil.Button();
        JPHuongDan = new javax.swing.JPanel();
        backgroundHelp2 = new Ultil.BackgroundHelp();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLHuongDan = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        JPGioiThieu = new javax.swing.JPanel();
        backgroundHelp1 = new Ultil.BackgroundHelp();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLGioiThieu = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        JPChienThang = new javax.swing.JPanel();
        backgroundHelp3 = new Ultil.BackgroundHelp();
        jLabel38 = new javax.swing.JLabel();
        btvictoryTroVe = new Ultil.Button();
        btVictoryChoiLai = new Ultil.Button();
        jLabel39 = new javax.swing.JLabel();
        JPThuaCuoc = new javax.swing.JPanel();
        backgroundDialog1 = new Ultil.BackgroundDialog();
        btThuaCuocTroVe = new Ultil.Button();
        btThuaCuocChoiMoi = new Ultil.Button();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jthongbaothuacuoc = new javax.swing.JLabel();
        background1 = new Ultil.Background();
        jLabel2 = new javax.swing.JLabel();
        jthongbao = new javax.swing.JLabel();
        btSoi = new Ultil.Button();
        btBapcai = new Ultil.Button();
        btDe = new Ultil.Button();
        jLabel4 = new javax.swing.JLabel();
        buttonPhai = new javax.swing.JLabel();
        buttonTrai = new javax.swing.JLabel();
        jbo2deanbapcai = new javax.swing.JLabel();
        jbo1deanbapcai = new javax.swing.JLabel();
        jbo2soiande = new javax.swing.JLabel();
        jbo1soiande = new javax.swing.JLabel();
        jPmessenger = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jlLuatChoi = new javax.swing.JLabel();
        jlGioiThieu = new javax.swing.JLabel();
        jlHuongDan = new javax.swing.JLabel();
        jLThoat = new javax.swing.JLabel();
        jPMessangeCartoon = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jPMessangeCartoon1 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setLayout(null);

        JPMoDau.setLayout(null);

        jLabel25.setFont(new java.awt.Font("Jokerman", 1, 48)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 11, 104));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Người Lái Đò");
        JPMoDau.add(jLabel25);
        jLabel25.setBounds(0, 200, 1270, 100);

        buttonMoDau.setForeground(new java.awt.Color(51, 51, 255));
        buttonMoDau.setText("Bắt Đầu");
        buttonMoDau.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        buttonMoDau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMoDauActionPerformed(evt);
            }
        });
        JPMoDau.add(buttonMoDau);
        buttonMoDau.setBounds(540, 610, 180, 60);

        jLabel26.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Hãy Bắt Đầu Trò Chơi Của Bạn");
        JPMoDau.add(jLabel26);
        jLabel26.setBounds(10, 500, 1270, 50);

        jLabel27.setBackground(new java.awt.Color(255, 102, 102));
        jLabel27.setFont(new java.awt.Font("Serif", 2, 24)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 102, 102));
        jLabel27.setText("Trò chơi giải trí vui nhộn");
        JPMoDau.add(jLabel27);
        jLabel27.setBounds(650, 290, 250, 40);

        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Anh-phong-cach-anime-than-tien-dep.jpg"))); // NOI18N
        jLabel51.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        JPMoDau.add(jLabel51);
        jLabel51.setBounds(0, 0, 1270, 770);

        add(JPMoDau);
        JPMoDau.setBounds(0, 0, 1270, 770);

        JPTienTrinh.setLayout(null);

        btTienTrinhHuy.setText("Hủy");
        btTienTrinhHuy.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btTienTrinhHuy.setRadius(30);
        btTienTrinhHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTienTrinhHuyActionPerformed(evt);
            }
        });

        btTienTrinhTiepTuc.setText("Tiếp Tục");
        btTienTrinhTiepTuc.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btTienTrinhTiepTuc.setRadius(30);
        btTienTrinhTiepTuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTienTrinhTiepTucActionPerformed(evt);
            }
        });

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/cute.png"))); // NOI18N
        jLabel54.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel55.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 51, 51));
        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel55.setText("Cảnh Báo !");

        jLabel56.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(255, 153, 153));
        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel56.setText("Nếu bạn thoát ra bây giờ");

        jthongbaothuacuoc1.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jthongbaothuacuoc1.setForeground(new java.awt.Color(255, 153, 153));
        jthongbaothuacuoc1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jthongbaothuacuoc1.setText("Bạn sẽ bị mất tiến trình chơi");

        javax.swing.GroupLayout backgroundDialog2Layout = new javax.swing.GroupLayout(backgroundDialog2);
        backgroundDialog2.setLayout(backgroundDialog2Layout);
        backgroundDialog2Layout.setHorizontalGroup(
            backgroundDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundDialog2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btTienTrinhHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addComponent(btTienTrinhTiepTuc, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75))
            .addComponent(jLabel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel55, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(backgroundDialog2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jthongbaothuacuoc1, javax.swing.GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
        );
        backgroundDialog2Layout.setVerticalGroup(
            backgroundDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundDialog2Layout.createSequentialGroup()
                .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jthongbaothuacuoc1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(backgroundDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btTienTrinhHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btTienTrinhTiepTuc, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
        );

        JPTienTrinh.add(backgroundDialog2);
        backgroundDialog2.setBounds(380, 190, 539, 329);

        add(JPTienTrinh);
        JPTienTrinh.setBounds(0, 0, 1270, 770);

        JPLuatChoi.setLayout(null);

        jLabel42.setFont(new java.awt.Font("Verdana", 3, 36)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 0, 0));
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setText("Luật Chơi");
        JPLuatChoi.add(jLabel42);
        jLabel42.setBounds(0, 100, 1270, 70);

        jLabel40.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("Biết rằng thuyền chỉ chở tối đa được 2 người (trừ bác lái đò - thuyền chỉ chở thêm được 1 con vật)");
        JPLuatChoi.add(jLabel40);
        jLabel40.setBounds(280, 260, 750, 20);

        jLabel41.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 255, 255));
        jLabel41.setText("Nếu sói và dê đứng riêng với nhau (không có mặt bác lái đò) thì sói sẽ ăn thịt dê");
        JPLuatChoi.add(jLabel41);
        jLabel41.setBounds(280, 290, 620, 21);

        jLabel43.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(0, 255, 255));
        jLabel43.setText("Nếu dê và bắp cải đứng riêng với nhau (không có mặt bác lái đò và sói) thì dê sẽ ăn bắp cải");
        JPLuatChoi.add(jLabel43);
        jLabel43.setBounds(280, 320, 710, 21);

        jLabel47.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText("Ký hiệu bờ sông mà sói, dê, bắp cải và bác lái đò đang đứng là 1, bờ sông bên kia là 2");
        JPLuatChoi.add(jLabel47);
        jLabel47.setBounds(280, 350, 670, 21);

        jLabel48.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("Ta cần đưa 3 con vật qua sông mà để chúng không ăn nhau");
        JPLuatChoi.add(jLabel48);
        jLabel48.setBounds(280, 380, 480, 21);

        btLuatChoi.setForeground(new java.awt.Color(255, 0, 255));
        btLuatChoi.setText("Đã Hiểu");
        btLuatChoi.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        btLuatChoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLuatChoiActionPerformed(evt);
            }
        });
        JPLuatChoi.add(btLuatChoi);
        btLuatChoi.setBounds(540, 610, 180, 50);

        add(JPLuatChoi);
        JPLuatChoi.setBounds(0, 0, 1270, 770);

        backgroundHelp2.setLayout(null);

        jLabel20.setFont(new java.awt.Font("Verdana", 1, 36)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(179, 39, 43));
        jLabel20.setText("Hướng Dẫn");
        backgroundHelp2.add(jLabel20);
        jLabel20.setBounds(390, 50, 240, 60);

        jLabel21.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 51, 255));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Để chiến thắng Game \"Người Lái Đò\" bạn cần thực hiện 1 số bước sau:");
        backgroundHelp2.add(jLabel21);
        jLabel21.setBounds(10, 190, 960, 24);

        jLabel22.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Bước 1: Ban đầu tại vị trí bờ 1, ta đưa dê lên thuyền và cho dê sang bờ 2");
        backgroundHelp2.add(jLabel22);
        jLabel22.setBounds(150, 220, 540, 24);

        jLabel23.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Bước 2: Cho thuyền trống, từ bờ 2 trở về bờ 1, lúc này bờ 2 chỉ có dê");
        backgroundHelp2.add(jLabel23);
        jLabel23.setBounds(150, 250, 520, 24);

        jLabel24.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText(" Bước 5: Nếu bờ 2 đang có sói, ta sẽ đưa bắp cải từ bờ 1 qua (vì sói không ăn bắp cải), và ngược lại");
        backgroundHelp2.add(jLabel24);
        jLabel24.setBounds(150, 340, 750, 24);

        jLabel28.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel28.setText(" Bước 3: Đưa sói hoặc bắp cải lên thuyền và cho chúng sang bờ 2");
        backgroundHelp2.add(jLabel28);
        jLabel28.setBounds(150, 280, 520, 24);

        jLabel29.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel29.setText(" Bước 4: Tại bờ 2 ta sẽ đón dê lên thuyền và cho nó quay lại bờ 1, lúc này tại bờ 2 chỉ còn sói hoặc bắp cải");
        backgroundHelp2.add(jLabel29);
        jLabel29.setBounds(150, 310, 790, 24);

        jLHuongDan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/delete.png"))); // NOI18N
        jLHuongDan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLHuongDanMouseClicked(evt);
            }
        });
        backgroundHelp2.add(jLHuongDan);
        jLHuongDan.setBounds(930, 20, 32, 32);

        jLabel33.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        jLabel33.setText(" Bước 6: Lúc này ta tiếp tục cho thuyền trống, từ bờ 2 trở về bờ 1");
        backgroundHelp2.add(jLabel33);
        jLabel33.setBounds(150, 370, 750, 24);

        jLabel34.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        jLabel34.setText(" Bước 7: Tại vị trí bờ 1, ta đưa nốt lên lên thuyền sang bờ 2, đến đây ta đã đưa đủ 3 con vật sang sông");
        backgroundHelp2.add(jLabel34);
        jLabel34.setBounds(150, 400, 800, 24);

        jLabel35.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("Trước tiên chúng ta cần lưu ý rằng: \"không để cặp (sói, dê) và (dê, bắp cải) ở 1 mình với nhau\"");
        backgroundHelp2.add(jLabel35);
        jLabel35.setBounds(150, 110, 720, 24);

        jLabel36.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("Gọi ví trí bờ ban đầu là \"bờ 1\", vị trí bờ 3 con vật muốn sang là \"bờ 2\"");
        backgroundHelp2.add(jLabel36);
        jLabel36.setBounds(150, 140, 540, 24);

        jLabel37.setFont(new java.awt.Font("Serif", 0, 24)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 0, 0));
        jLabel37.setText("Chúc các bạn thành công!");
        backgroundHelp2.add(jLabel37);
        jLabel37.setBounds(370, 470, 300, 30);

        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/hinh-nen-powerpoint-thien-nhien_103813676.jpg"))); // NOI18N
        backgroundHelp2.add(jLabel31);
        jLabel31.setBounds(10, 10, 960, 600);

        javax.swing.GroupLayout JPHuongDanLayout = new javax.swing.GroupLayout(JPHuongDan);
        JPHuongDan.setLayout(JPHuongDanLayout);
        JPHuongDanLayout.setHorizontalGroup(
            JPHuongDanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPHuongDanLayout.createSequentialGroup()
                .addContainerGap(151, Short.MAX_VALUE)
                .addComponent(backgroundHelp2, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(139, 139, 139))
        );
        JPHuongDanLayout.setVerticalGroup(
            JPHuongDanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPHuongDanLayout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(backgroundHelp2, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(82, Short.MAX_VALUE))
        );

        add(JPHuongDan);
        JPHuongDan.setBounds(0, 0, 1270, 770);

        backgroundHelp1.setLayout(null);

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 36)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(179, 39, 43));
        jLabel9.setText("Giới Thiệu");
        backgroundHelp1.add(jLabel9);
        jLabel9.setBounds(390, 50, 210, 60);

        jLabel10.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 51, 255));
        jLabel10.setText("Bài toán \"Người Lái Đò\" là một trong những câu đố dân gian nổi tiếng, thử thách trí tuệ người chơi");
        backgroundHelp1.add(jLabel10);
        jLabel10.setBounds(140, 120, 740, 24);

        jLabel11.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 51, 255));
        jLabel11.setText("Chính vì sự hấp dẫn thử thách trí tuệ. Bài toán \"Người Lái Đò\" được nhóm chọn, và xây dựng thuật toán");
        backgroundHelp1.add(jLabel11);
        jLabel11.setBounds(110, 160, 780, 24);

        jLabel12.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 51, 255));
        jLabel12.setText("giải quyết bài toán. Với mục đích giải trí và thử thách trí tuệ. Game \"Người Lái Đò\"  được chúng em");
        backgroundHelp1.add(jLabel12);
        jLabel12.setBounds(130, 200, 750, 24);

        jLabel13.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 51, 255));
        jLabel13.setText("cùng game dân gian của nhóm em.");
        backgroundHelp1.add(jLabel13);
        jLabel13.setBounds(120, 310, 270, 24);

        jLabel14.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel14.setText("Người dựng Game");
        backgroundHelp1.add(jLabel14);
        jLabel14.setBounds(630, 380, 140, 21);

        jLabel15.setFont(new java.awt.Font("Pristina", 0, 18)); // NOI18N
        jLabel15.setText("Trần Văn Trường");
        backgroundHelp1.add(jLabel15);
        jLabel15.setBounds(650, 410, 100, 22);

        jLabel16.setText("Hà Nam, ngày 28 /tháng 4 /năm 2023");
        backgroundHelp1.add(jLabel16);
        jLabel16.setBounds(610, 350, 200, 16);

        jLabel17.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 51, 255));
        jLabel17.setText("xây dựng, để đem lại niềm vui và giải trí, cùng trong quá trình làm bài giúp chúng em tích lũy kiến thức");
        backgroundHelp1.add(jLabel17);
        jLabel17.setBounds(120, 240, 780, 24);

        jLabel18.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 51, 255));
        jLabel18.setText("và hiểu sâu hơn về AI. Cuối cùng, Xin chúc thầy công cùng các bạn học có thêm chút thời gian thư giãn");
        backgroundHelp1.add(jLabel18);
        jLabel18.setBounds(120, 280, 780, 24);

        jLGioiThieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/delete.png"))); // NOI18N
        jLGioiThieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLGioiThieuMouseClicked(evt);
            }
        });
        backgroundHelp1.add(jLGioiThieu);
        jLGioiThieu.setBounds(930, 20, 32, 32);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/0057caf44a0ab23.png"))); // NOI18N
        backgroundHelp1.add(jLabel8);
        jLabel8.setBounds(10, 10, 960, 600);

        javax.swing.GroupLayout JPGioiThieuLayout = new javax.swing.GroupLayout(JPGioiThieu);
        JPGioiThieu.setLayout(JPGioiThieuLayout);
        JPGioiThieuLayout.setHorizontalGroup(
            JPGioiThieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPGioiThieuLayout.createSequentialGroup()
                .addContainerGap(151, Short.MAX_VALUE)
                .addComponent(backgroundHelp1, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(139, 139, 139))
        );
        JPGioiThieuLayout.setVerticalGroup(
            JPGioiThieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPGioiThieuLayout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(backgroundHelp1, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(82, Short.MAX_VALUE))
        );

        add(JPGioiThieu);
        JPGioiThieu.setBounds(0, 0, 1270, 770);

        JPChienThang.setLayout(null);

        backgroundHelp3.setLayout(null);

        jLabel38.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 204, 0));
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("Xin Chúc Mừng Bạn Đã Là Người Chiến Thắng");
        backgroundHelp3.add(jLabel38);
        jLabel38.setBounds(10, 360, 800, 60);

        btvictoryTroVe.setForeground(new java.awt.Color(255, 0, 255));
        btvictoryTroVe.setText("Trở Về");
        btvictoryTroVe.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        btvictoryTroVe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btvictoryTroVeActionPerformed(evt);
            }
        });
        backgroundHelp3.add(btvictoryTroVe);
        btvictoryTroVe.setBounds(140, 450, 200, 50);

        btVictoryChoiLai.setForeground(new java.awt.Color(255, 0, 255));
        btVictoryChoiLai.setText("Chơi Lại");
        btVictoryChoiLai.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        btVictoryChoiLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btVictoryChoiLaiActionPerformed(evt);
            }
        });
        backgroundHelp3.add(btVictoryChoiLai);
        btVictoryChoiLai.setBounds(470, 450, 200, 50);

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/thumb-192.png"))); // NOI18N
        backgroundHelp3.add(jLabel39);
        jLabel39.setBounds(0, 0, 820, 550);

        JPChienThang.add(backgroundHelp3);
        backgroundHelp3.setBounds(230, 110, 820, 550);

        add(JPChienThang);
        JPChienThang.setBounds(0, 0, 1270, 770);

        JPThuaCuoc.setLayout(null);

        btThuaCuocTroVe.setText("Trở Về");
        btThuaCuocTroVe.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btThuaCuocTroVe.setRadius(30);
        btThuaCuocTroVe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThuaCuocTroVeActionPerformed(evt);
            }
        });

        btThuaCuocChoiMoi.setText("Chơi Mới");
        btThuaCuocChoiMoi.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btThuaCuocChoiMoi.setRadius(30);
        btThuaCuocChoiMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThuaCuocChoiMoiActionPerformed(evt);
            }
        });

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/cute.png"))); // NOI18N
        jLabel44.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel45.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 51, 51));
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setText("Ôi Không !");

        jLabel46.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(0, 255, 51));
        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel46.setText("Bạn đã thất bại...");

        jthongbaothuacuoc.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jthongbaothuacuoc.setForeground(new java.awt.Color(0, 255, 51));
        jthongbaothuacuoc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jthongbaothuacuoc.setText("Sói ăn Dê rồi kìa");

        javax.swing.GroupLayout backgroundDialog1Layout = new javax.swing.GroupLayout(backgroundDialog1);
        backgroundDialog1.setLayout(backgroundDialog1Layout);
        backgroundDialog1Layout.setHorizontalGroup(
            backgroundDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundDialog1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(btThuaCuocTroVe, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(btThuaCuocChoiMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
            .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel45, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(backgroundDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jthongbaothuacuoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        backgroundDialog1Layout.setVerticalGroup(
            backgroundDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundDialog1Layout.createSequentialGroup()
                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jthongbaothuacuoc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(backgroundDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btThuaCuocTroVe, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btThuaCuocChoiMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
        );

        JPThuaCuoc.add(backgroundDialog1);
        backgroundDialog1.setBounds(380, 190, 539, 329);

        add(JPThuaCuoc);
        JPThuaCuoc.setBounds(0, 0, 1270, 770);

        background1.setBackground(new java.awt.Color(204, 255, 204));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setText("Hãy Bắt Đầu Trò Chơi Của Bạn:");

        jthongbao.setFont(new java.awt.Font("Times New Roman", 2, 18)); // NOI18N
        jthongbao.setText("Cùng chơi nào ^^");

        btSoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/wolf (1).png"))); // NOI18N
        btSoi.setRadius(50);
        btSoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSoiActionPerformed(evt);
            }
        });

        btBapcai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/cabbage (1).png"))); // NOI18N
        btBapcai.setRadius(50);
        btBapcai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBapcaiActionPerformed(evt);
            }
        });

        btDe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/goat (1).png"))); // NOI18N
        btDe.setRadius(50);
        btDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDeActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 255));
        jLabel4.setText("Game Người Lái Đò do nhóm 9 thực hiện");

        buttonPhai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/right.png"))); // NOI18N
        buttonPhai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonPhaiMouseClicked(evt);
            }
        });

        buttonTrai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/left-arrow.png"))); // NOI18N
        buttonTrai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonTraiMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addGap(119, 119, 119)
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(background1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(36, 36, 36)
                        .addComponent(jthongbao, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(background1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(138, 138, 138)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, background1Layout.createSequentialGroup()
                .addContainerGap(272, Short.MAX_VALUE)
                .addComponent(buttonTrai, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(btSoi, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(btDe, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(btBapcai, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(buttonPhai, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(222, 222, 222))
        );
        background1Layout.setVerticalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jthongbao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buttonTrai, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                    .addComponent(btSoi, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                    .addComponent(btDe, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                    .addComponent(btBapcai, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                    .addComponent(buttonPhai, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE))
        );

        add(background1);
        background1.setBounds(0, 590, 1260, 173);

        jbo2deanbapcai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/200w.gif"))); // NOI18N
        add(jbo2deanbapcai);
        jbo2deanbapcai.setBounds(910, 140, 240, 230);

        jbo1deanbapcai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/200w.gif"))); // NOI18N
        add(jbo1deanbapcai);
        jbo1deanbapcai.setBounds(270, 150, 240, 230);

        jbo2soiande.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/200w.gif"))); // NOI18N
        add(jbo2soiande);
        jbo2soiande.setBounds(930, 240, 240, 230);

        jbo1soiande.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/200w.gif"))); // NOI18N
        add(jbo1soiande);
        jbo1soiande.setBounds(190, 230, 240, 230);

        jPmessenger.setBackground(new java.awt.Color(255, 255, 255));
        jPmessenger.setLayout(null);

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel5.setText("Hãy chọn 1 con");
        jPmessenger.add(jLabel5);
        jLabel5.setBounds(30, 30, 100, 17);

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel6.setText("vật bạn muốn đưa ");
        jPmessenger.add(jLabel6);
        jLabel6.setBounds(20, 50, 120, 17);

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel7.setText("qua sông");
        jPmessenger.add(jLabel7);
        jLabel7.setBounds(50, 70, 60, 17);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/speech-bubble (1).png"))); // NOI18N
        jPmessenger.add(jLabel3);
        jLabel3.setBounds(10, 0, 130, 128);

        add(jPmessenger);
        jPmessenger.setBounds(610, 200, 150, 130);

        jlLuatChoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/yellow-card.png"))); // NOI18N
        jlLuatChoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlLuatChoiMouseClicked(evt);
            }
        });
        add(jlLuatChoi);
        jlLuatChoi.setBounds(1080, 0, 32, 40);

        jlGioiThieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/question-mark.png"))); // NOI18N
        jlGioiThieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlGioiThieuMouseClicked(evt);
            }
        });
        add(jlGioiThieu);
        jlGioiThieu.setBounds(1200, 0, 30, 40);

        jlHuongDan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/light-bulb.png"))); // NOI18N
        jlHuongDan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlHuongDanMouseClicked(evt);
            }
        });
        add(jlHuongDan);
        jlHuongDan.setBounds(1140, 0, 40, 40);

        jLThoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/logout.png"))); // NOI18N
        jLThoat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLThoatMouseClicked(evt);
            }
        });
        add(jLThoat);
        jLThoat.setBounds(10, 0, 32, 40);

        jPMessangeCartoon.setLayout(null);

        jLabel19.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel19.setText("Chúng tôi muốn");
        jPMessangeCartoon.add(jLabel19);
        jLabel19.setBounds(30, 36, 100, 30);

        jLabel32.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel32.setText("qua sông");
        jPMessangeCartoon.add(jLabel32);
        jLabel32.setBounds(50, 70, 60, 20);

        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/bubble-chat.png"))); // NOI18N
        jPMessangeCartoon.add(jLabel30);
        jLabel30.setBounds(10, 10, 128, 130);

        add(jPMessangeCartoon);
        jPMessangeCartoon.setBounds(190, 150, 150, 150);

        jPMessangeCartoon1.setLayout(null);

        jLabel49.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel49.setText("Bạn thật giỏi.");
        jPMessangeCartoon1.add(jLabel49);
        jLabel49.setBounds(40, 40, 80, 30);

        jLabel50.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel50.setText("Cảm ơn bạn");
        jPMessangeCartoon1.add(jLabel50);
        jLabel50.setBounds(40, 70, 80, 20);

        jLabel52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/bubble-chat.png"))); // NOI18N
        jPMessangeCartoon1.add(jLabel52);
        jLabel52.setBounds(10, 10, 128, 130);

        add(jPMessangeCartoon1);
        jPMessangeCartoon1.setBounds(870, 100, 150, 150);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/pngtree-creek-river-water-png-image_3897381.png"))); // NOI18N
        add(jLabel1);
        jLabel1.setBounds(0, 0, 1270, 590);
    }// </editor-fold>//GEN-END:initComponents

    private void btSoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSoiActionPerformed
        // Tắt messenger
        jPmessenger.setVisible(false);
        jPMessangeCartoon.setVisible(false);
        
        if(benbo1 == true && benbo2 == false){ // Bờ 1
            if(buttonSoiBo1 == true && trenthuyen == false){ // Nếu như thuyền đang trống
                animatePointNew = new Point(400, 300);
                ViewAnimation(1, animatePointNew);
                soi.setImageLocation(animatePointNew);
                buttonSoiBo1 = false;
                trenthuyen = true;
            }
            else if(buttonSoiBo1 == true && buttonDeBo1 == false && buttonDeBo2 == false){ // Nếu như thuyền đang có dê và ở bờ 1
                // Đưa dê xuống
                animatePointNew = new Point(250, 260);
                ViewAnimation(2, animatePointNew);
                de.setImageLocation(animatePointNew);
                buttonDeBo1 = true;
                // Đưa sói lên
                animatePointNew = new Point(400, 300);
                ViewAnimation(1, animatePointNew);
                soi.setImageLocation(animatePointNew);
                buttonSoiBo1 = false;
                trenthuyen = true;
            }
            else if(buttonSoiBo1 == true && buttonBapcaiBo1 == false && buttonBapcaiBo2 == false){ // Nếu như đang có bắp cải
                // Đưa Bắp cải xuống
                animatePointNew = new Point(370, 220);
                ViewAnimation(3, animatePointNew);
                bapcai.setImageLocation(animatePointNew);
                buttonBapcaiBo1 = true;
                // Đưa sói lên
                animatePointNew = new Point(400, 300);
                ViewAnimation(1, animatePointNew);
                soi.setImageLocation(animatePointNew);
                buttonSoiBo1 = false;
                trenthuyen = true;
            }
            else if(buttonSoiBo1 == false && trenthuyen == true){ // Nếu sói đang trên thuyền
                // Đưa sói xuống
                animatePointNew = new Point(250, 370);
                ViewAnimation(1, animatePointNew);
                soi.setImageLocation(animatePointNew);
                buttonSoiBo1 = true;
                trenthuyen = false;
                luatchoi = true;
            }
        }
        // Bờ 2
        else if(benbo1 == false && benbo2 == true){
            if(buttonSoiBo2 == true && trenthuyen == false){ // Nếu như bờ có soi và thuyền đang trống
                animatePointNew = new Point(713, 300);
                ViewAnimation(1, animatePointNew);
                soi.setImageLocation(animatePointNew);
                buttonSoiBo2 = false; // Xét lại không có sói trên bờ
                trenthuyen = true;
            }
            else if(buttonSoiBo2 == true && buttonDeBo2 == false && buttonDeBo2 == false){ // Nếu như thuyền đang có dê
                // Đưa dê xuống
                animatePointNew = new Point(970, 290);
                ViewAnimation(2, animatePointNew);
                de.setImageLocation(animatePointNew);
                buttonDeBo2 = true;
                // Đưa sói lên
                animatePointNew = new Point(713, 300);
                ViewAnimation(1, animatePointNew);
                soi.setImageLocation(animatePointNew);
                buttonSoiBo2 = false; // Xét lại không có sói trên bờ
                trenthuyen = true;
            }
            else if(buttonSoiBo2 == true && buttonBapcaiBo2 == false && buttonBapcaiBo1 == false){ // Nếu như đang có bắp cải
                // Đưa Bắp cải xuống
                animatePointNew = new Point(980, 220);
                ViewAnimation(3, animatePointNew);
                bapcai.setImageLocation(animatePointNew);
                buttonBapcaiBo2 = true;
                // Đưa sói lên
                animatePointNew = new Point(713, 300);
                ViewAnimation(1, animatePointNew);
                soi.setImageLocation(animatePointNew);
                buttonSoiBo2 = false; // Xét lại không có sói trên bờ
                trenthuyen = true;
            }
            else if(buttonSoiBo1 == false && trenthuyen == true){
                // Đưa sói xuống
                animatePointNew = new Point(1010, 380);
                ViewAnimation(1, animatePointNew);
                soi.setImageLocation(animatePointNew);
                buttonSoiBo2 = true;
                trenthuyen = false;
            }
        }
    }//GEN-LAST:event_btSoiActionPerformed

    private void btBapcaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBapcaiActionPerformed
        // Tắt messager
        jPmessenger.setVisible(false);
        jPMessangeCartoon.setVisible(false);
        
        if(benbo1 == true && benbo2 == false){ // Bờ 1
            if(buttonBapcaiBo1 == true && trenthuyen == false){ // Nếu như thuyền đang trống
                animatePointNew = new Point(435, 345);
                ViewAnimation(3, animatePointNew);
                bapcai.setImageLocation(animatePointNew);
                buttonBapcaiBo1 = false;
                trenthuyen = true;
            }
            else if(buttonBapcaiBo1 == true && buttonSoiBo1 == false && buttonSoiBo2 == false){ // Nếu như đang có sói trên thuyền
                // Đưa sói xuống
                animatePointNew = new Point(250, 370);
                ViewAnimation(1, animatePointNew);
                soi.setImageLocation(animatePointNew);
                buttonSoiBo1 = true;
                // Đưa bắp cải lên
                animatePointNew = new Point(435, 345);
                ViewAnimation(3, animatePointNew);
                bapcai.setImageLocation(animatePointNew);
                buttonBapcaiBo1 = false;
                trenthuyen = true;
            }
            else if(buttonBapcaiBo1 == true && buttonDeBo1 == false && buttonDeBo2 == false){ // Nếu như thuyền đang có dê trên thuyền
                // Đưa dê xuống
                animatePointNew = new Point(250, 260);
                ViewAnimation(2, animatePointNew);
                de.setImageLocation(animatePointNew);
                buttonDeBo1 = true;
                // Đưa bắp cải lên
                animatePointNew = new Point(435, 345);
                ViewAnimation(3, animatePointNew);
                bapcai.setImageLocation(animatePointNew);
                buttonBapcaiBo1 = false;
                trenthuyen = true;
            }
            else if(buttonBapcaiBo1 == false && trenthuyen == true){ // Nếu bắp cải đang trên thuyền
                // Đưa bắp cải xuống
                animatePointNew = new Point(370, 220);
                ViewAnimation(3, animatePointNew);
                bapcai.setImageLocation(animatePointNew);
                buttonBapcaiBo1 = true;
                trenthuyen = false;
                luatchoi = true;
            }
        }
        // Bờ 2
        else if(benbo1 == false && benbo2 == true){ 
            if(buttonBapcaiBo2 == true && trenthuyen == false){ // Nếu như bờ có bắp cải và thuyền đang trống
                animatePointNew = new Point(742, 345);
                ViewAnimation(3, animatePointNew);
                bapcai.setImageLocation(animatePointNew);
                buttonBapcaiBo2 = false; // Xét lại không có bắp cải trên bờ
                trenthuyen = true;
            }
            else if(buttonBapcaiBo2 == true && buttonSoiBo2 == false && buttonSoiBo1 == false){ // Nếu như thuyền đang có sói
                // Đưa sói xuống
                animatePointNew = new Point(1010, 380);
                ViewAnimation(1, animatePointNew);
                soi.setImageLocation(animatePointNew);
                buttonSoiBo2 = true;
                // Đưa bắp cải lên
                animatePointNew = new Point(742, 345);
                ViewAnimation(3, animatePointNew);
                bapcai.setImageLocation(animatePointNew);
                buttonBapcaiBo2 = false; // Xét lại không có bắp cải trên bờ
                trenthuyen = true;
            }
            else if(buttonBapcaiBo2 == true && buttonDeBo2 == false && buttonDeBo2 == false){ // Nếu như thuyền đang có dê
                // Đưa dê xuống
                animatePointNew = new Point(970, 290);
                ViewAnimation(2, animatePointNew);
                de.setImageLocation(animatePointNew);
                buttonDeBo2 = true;
                // Đưa bắp cải lên
                animatePointNew = new Point(742, 345);
                ViewAnimation(3, animatePointNew);
                bapcai.setImageLocation(animatePointNew);
                buttonBapcaiBo2 = false; // Xét lại không có bắp cải trên bờ
                trenthuyen = true;
            }
            else if(buttonBapcaiBo2 == false && trenthuyen == true){ // Nếu như bắp cải đang trên thuyền
                animatePointNew = new Point(980, 220);
                ViewAnimation(3, animatePointNew);
                bapcai.setImageLocation(animatePointNew);
                buttonBapcaiBo2 = true;
                trenthuyen = false;
            }
        }
    }//GEN-LAST:event_btBapcaiActionPerformed

    private void btDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDeActionPerformed
        // Tắt messager
        jPmessenger.setVisible(false);
        jPMessangeCartoon.setVisible(false);
        
        if(benbo1 == true && benbo2 == false){ // Bờ 1
            if(buttonDeBo1 == true && trenthuyen == false){ // Nếu như thuyền đang trống
                animatePointNew = new Point(410, 305);
                ViewAnimation(2, animatePointNew);
                de.setImageLocation(animatePointNew);
                buttonDeBo1 = false;
                trenthuyen = true;
            }
            else if(buttonDeBo1 == true && buttonSoiBo1 == false && buttonSoiBo2 == false){ // Nếu như đang có sói trên thuyền
                // Đưa sói xuống
                animatePointNew = new Point(250, 370);
                ViewAnimation(1, animatePointNew);
                soi.setImageLocation(animatePointNew);
                buttonSoiBo1 = true;
                // Đưa dê lên
                animatePointNew = new Point(410, 305);
                ViewAnimation(2, animatePointNew);
                de.setImageLocation(animatePointNew);
                buttonDeBo1 = false;
                trenthuyen = true;
            }
            else if(buttonDeBo1 == true && buttonBapcaiBo1 == false && buttonBapcaiBo2 == false){ // Nếu như đang có bắp cải
                // Đưa Bắp cải xuống
                animatePointNew = new Point(370, 220);
                ViewAnimation(3, animatePointNew);
                bapcai.setImageLocation(animatePointNew);
                buttonBapcaiBo1 = true;
                // Đưa dê lên
                animatePointNew = new Point(410, 305);
                ViewAnimation(2, animatePointNew);
                de.setImageLocation(animatePointNew);
                buttonDeBo1 = false;
                trenthuyen = true;
            }
            else if(buttonDeBo1 == false && trenthuyen == true){ // Nếu dê đang trên thuyền
                // Đưa sói xuống
                animatePointNew = new Point(250, 260);
                ViewAnimation(2, animatePointNew);
                de.setImageLocation(animatePointNew);
                buttonDeBo1 = true;
                trenthuyen = false;
                luatchoi = true;
            }
        }
        // Bờ 2
        else if(benbo1 == false && benbo2 == true){ 
            if(buttonDeBo2 == true && trenthuyen == false){ // Nếu như bờ có dê và thuyền đang trống
                animatePointNew = new Point(720, 305);
                ViewAnimation(2, animatePointNew);
                de.setImageLocation(animatePointNew);
                buttonDeBo2 = false; // Xét lại không có dê trên bờ
                trenthuyen = true;
            }
            else if(buttonDeBo2 == true && buttonSoiBo2 == false && buttonSoiBo1 == false){ // Nếu như thuyền đang có sói
                // Đưa sói xuống
                animatePointNew = new Point(1010, 380);
                ViewAnimation(1, animatePointNew);
                soi.setImageLocation(animatePointNew);
                buttonBapcaiBo2 = true;
                // Đưa dê lên
                animatePointNew = new Point(720, 305);
                ViewAnimation(2, animatePointNew);
                de.setImageLocation(animatePointNew);
                buttonDeBo2 = false; // Xét lại không có dê trên bờ
                trenthuyen = true;
            }
            else if(buttonDeBo2 == true && buttonBapcaiBo2 == false && buttonBapcaiBo1 == false){ // Nếu như đang có bắp cải trên thuyền và k có ở bờ 1
                // Đưa Bắp cải xuống
                animatePointNew = new Point(980, 220);
                ViewAnimation(3, animatePointNew);
                bapcai.setImageLocation(animatePointNew);
                buttonBapcaiBo2 = true;
                // Đưa dê lên
                animatePointNew = new Point(720, 305);
                ViewAnimation(2, animatePointNew);
                de.setImageLocation(animatePointNew);
                buttonDeBo2 = false; // Xét lại không có dê trên bờ
                trenthuyen = true;
            }
            else if(buttonDeBo2 == false && trenthuyen == true){
                // Đưa dê xuống
                animatePointNew = new Point(970, 290);
                ViewAnimation(2, animatePointNew);
                de.setImageLocation(animatePointNew);
                buttonDeBo2 = true; // Xét lại có dê trên bờ
                trenthuyen = false;
            }
        }
    }//GEN-LAST:event_btDeActionPerformed

    // Xây dựng hàm ẩn hiện các nút sự kiện trong game
    private void ExamButton(){
        if(benbo1 == true && benbo2 == false){ // Bờ trái
            if(buttonSoiBo1 == false){ // Không có sói
                btSoi.setEnabled(false); // Tắt đi
                btSoi.setBorderColor(Color.red);
            }
            else{
                btSoi.setEnabled(true); // bật lên
                btSoi.setBorderColor(new Color(153,255,255));
            }
            if(buttonDeBo1 == false){ // Không có dê
                btDe.setEnabled(false); // Tắt đi
                btDe.setBorderColor(Color.red);
            }
            else{
                btDe.setEnabled(true); // bật lên
                btDe.setBorderColor(new Color(153,255,255));
            }
            if(buttonBapcaiBo1 == false){ // Không có bắp cải
                btBapcai.setEnabled(false); // Tắt đi
                btBapcai.setBorderColor(Color.red);
            }
            else{
                btBapcai.setEnabled(true); // bật lên
                btBapcai.setBorderColor(new Color(153,255,255));
            }
        }
        else if(benbo1 == false && benbo2 == true){ // Bờ phải
            if(buttonSoiBo2 == false){ // Không có sói
                btSoi.setEnabled(false); // Tắt đi
                btSoi.setBorderColor(Color.red);
            }
            else{
                btSoi.setEnabled(true); // bật lên
                btSoi.setBorderColor(new Color(153,255,255));
            }
            if(buttonDeBo2 == false){ // Không có dê
                btDe.setEnabled(false); // Tắt đi
                btDe.setBorderColor(Color.red);
            }
            else{
                btDe.setEnabled(true); // bật lên
                btDe.setBorderColor(new Color(153,255,255));
            }
            if(buttonBapcaiBo2 == false){ // Không có bắp cải
                btBapcai.setEnabled(false); // Tắt đi
                btBapcai.setBorderColor(Color.red);
            }
            else{
                btBapcai.setEnabled(true); // bật lên
                btBapcai.setBorderColor(new Color(153,255,255));
            }
        }
    }
    
    // Xây dựng hàm đánh giá
    private boolean FunctionEvaluation(boolean soi, boolean de, boolean bapcai, boolean benbo){ // Hàm này cần thỏa mãn điều kiện không để cặp (sói với dê) và (dê và bắp cải) ở với nhau mà không có mặt ai
        if( // Xét trạng thái bờ 1
           (soi == false && de == false && bapcai == false && benbo == false) || // Không có ai cả
           (soi == true && de == true && bapcai == true && benbo == false) || // Không có mặt bác lái đò
           (soi == false && de == true && bapcai == true && benbo == true) || // Không có sói
           (soi == true && de == false && bapcai == true && benbo == true) || // Không có dê
           (soi == true && de == true && bapcai == false && benbo == true) || // Không có bắp cả
           (soi == true && de == false && bapcai == true && benbo == false) || // Không có dê và bác lái đò
           (soi == false && de == true && bapcai == false && benbo == true) || // Không có sói và bắp cả
           (soi == false && de == true && bapcai == false && benbo == false) || // Không có sói và bắp cả và bác lái đò
           (soi == false && de == false && bapcai == true && benbo == false) || // Không có sói và dê và bác lái đò
           (soi == true && de == false && bapcai == false && benbo == false) || // Không có dê, bắp cải và bác lái đò
           (soi == true && de == true && bapcai == true && benbo == true)){ // Có đầy đủ mọi người
            return true;
        }
        return false;
    }
    
    private void buttonPhaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonPhaiMouseClicked
        jPMessangeCartoon.setVisible(false);
  
        System.out.println(benbo1);
        System.out.println(benbo2);
        // Khi thuyền đang ở bờ phải, sẽ khóa nút right
        if(benbo1 == false && benbo2 == true){ // bờ 2
            jthongbao.setText("Thuyền đang ở bờ 2, không thể qua phải được nữa ^^");
        }
        else if(benbo1 == true && benbo2 == false){ // bờ 1
            // Thực hiện cho thuyền chạy
            if(trenthuyen == false){ // Không có con nào trên thuyền
                jPmessenger.setVisible(true);
                jthongbao.setText("Bạn hãy chọn 1 con vật muốn đưa qua sông đi ^^");
            }
            else if(buttonSoiBo1 == false && trenthuyen == true && buttonSoiBo2 == false){ // Có sói trên thuyền
                jthongbao.setText("Ôi không!");
                animation2 = true;
                // Xét đổi sang trạng thái bờ phải
                benbo1 = false;
                benbo2 = true;
                
                // Đưa sói qua bờ
                animatePointNew = new Point(713, 300);
                ViewAnimation(1, animatePointNew);
                soi.setImageLocation(animatePointNew);
                // Thuyền chạy
                animatePointNew = new Point(710, 323);
                ViewAnimation(4, animatePointNew);
                thuyen.setImageLocation(animatePointNew);
                // Bác lái đò
                animatePointNew = new Point(840, 280);
                ViewAnimation(5, animatePointNew);
                nguoilaido.setImageLocation(animatePointNew);
            }
            else if(buttonDeBo1 == false && trenthuyen == true && buttonDeBo2 == false){ // Có dê trên thuyền
                jthongbao.setText("Lùi 1 bước, tiến 1 sải");
                animation2 = true;
                // Xét đổi sang trạng thái bờ phải
                benbo1 = false;
                benbo2 = true;
                
                // Đưa dê qua bờ
                animatePointNew = new Point(720, 305);
                ViewAnimation(2, animatePointNew);
                de.setImageLocation(animatePointNew);
                // Thuyền chạy
                animatePointNew = new Point(710, 323);
                ViewAnimation(4, animatePointNew);
                thuyen.setImageLocation(animatePointNew);
                // Bác lái đò
                animatePointNew = new Point(840, 280);
                ViewAnimation(5, animatePointNew);
                nguoilaido.setImageLocation(animatePointNew);
            }
            else if(buttonBapcaiBo1 == false && trenthuyen == true && buttonBapcaiBo2 == false){ // Có bắp cả trên thuyền
                jthongbao.setText("Chết Dở rồi bà zà ...");
                animation2 = true;
                // Xét đổi sang trạng thái bờ phải
                benbo1 = false;
                benbo2 = true;
                
                // Đưa bắp cải qua bờ
                animatePointNew = new Point(742, 345);
                ViewAnimation(3, animatePointNew);
                bapcai.setImageLocation(animatePointNew);
                // Thuyền chạy
                animatePointNew = new Point(710, 323);
                ViewAnimation(4, animatePointNew);
                thuyen.setImageLocation(animatePointNew);
                // Bác lái đò
                animatePointNew = new Point(840, 280);
                ViewAnimation(5, animatePointNew);
                nguoilaido.setImageLocation(animatePointNew);
            }
            
            // Thực hiện kiểm tra xem có thỏa mãn hay không
            if(FunctionEvaluation(buttonSoiBo1, buttonDeBo1, buttonBapcaiBo1, benbo1) == true){ // Thỏa mãn điều kiện 2 con vật không ăn thịt nhau
                endsoiande = false;
                enddeanbapcai = false;
            } 
            else{ // Không thỏa mãn 
                if(buttonSoiBo1 == false && buttonDeBo1 == true && buttonBapcaiBo1 == true){ // Dê ăn bắp cải
                    enddeanbapcai = true;
                    animatePointNew = new Point(290, 200);
                    ViewAnimation(2, animatePointNew);
                }
                if(buttonSoiBo1 == true && buttonDeBo1 == true && buttonBapcaiBo1 == false){ // Sói ăn dê
                    endsoiande = true;
                    animatePointNew = new Point(220, 270);
                    ViewAnimation(1, animatePointNew);
                }
            }
        }
    }//GEN-LAST:event_buttonPhaiMouseClicked

    private void buttonTraiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonTraiMouseClicked
        // Khi đang ở bờ trái, sẽ khóa nút left
        if(benbo1 == true && benbo2 == false){ // bờ 1
            jthongbao.setText("Thuyền đang ở bờ 1, không thể qua trái được nữa ^^");
        }
        else if(benbo1 == false && benbo2 == true){ // bờ 2
            // Thực hiện cho thuyền chạy
            if(trenthuyen == false && (buttonSoiBo2 == true || buttonDeBo2 == true || buttonBapcaiBo2 == true)){ // Không có con vật nào trên thuyền
                // Cho thuyền chở về
                jthongbao.setText("Let go!");
                // Xét đổi sang trạng thái bờ trái
                benbo1 = true;
                benbo2 = false;
                
                // Thuyền chạy
                animatePointNew = new Point(400, 323);
                ViewAnimation(4, animatePointNew);
                thuyen.setImageLocation(animatePointNew);
                // Bác lái đò
                animatePointNew = new Point(530, 280);
                ViewAnimation(5, animatePointNew);
                nguoilaido.setImageLocation(animatePointNew);
                // Xét lại nút
                ExamButton();
            }
            else if(buttonSoiBo2 == false && trenthuyen == true && buttonSoiBo1 == false){ // Có sói trên thuyền
                jthongbao.setText("Tiến Lên");
                animation2 = true;
                // Xét đổi sang trạng thái bờ trái
                benbo1 = true;
                benbo2 = false;
                
                // Đưa sói qua bờ
                animatePointNew = new Point(400, 300);
                ViewAnimation(1, animatePointNew);
                soi.setImageLocation(animatePointNew);
                // Thuyền chạy
                animatePointNew = new Point(400, 323);
                ViewAnimation(4, animatePointNew);
                thuyen.setImageLocation(animatePointNew);
                // Bác lái đò
                animatePointNew = new Point(530, 280);
                ViewAnimation(5, animatePointNew);
                nguoilaido.setImageLocation(animatePointNew);
            }
            else if(buttonDeBo2 == false && trenthuyen == true && buttonDeBo1 == false){ // Có dê trên thuyền
                jthongbao.setText("Let go!");
                animation2 = true;
                // Xét đổi sang trạng thái bờ trái
                benbo1 = true;
                benbo2 = false;
                
                // Đưa dê qua bờ
                animatePointNew = new Point(410, 305);
                ViewAnimation(2, animatePointNew);
                de.setImageLocation(animatePointNew);
                // Thuyền chạy
                animatePointNew = new Point(400, 323);
                ViewAnimation(4, animatePointNew);
                thuyen.setImageLocation(animatePointNew);
                // Bác lái đò
                animatePointNew = new Point(530, 280);
                ViewAnimation(5, animatePointNew);
                nguoilaido.setImageLocation(animatePointNew);
            }
            else if(buttonBapcaiBo2 == false && trenthuyen == true && buttonBapcaiBo1 == false){ // Có bắp cả trên thuyền
                jthongbao.setText("Sắp chiến thắng rồi bạn ơi!");
                animation2 = true;
                // Xét đổi sang trạng thái bờ trái
                benbo1 = true;
                benbo2 = false;
                
                // Đưa bắp cải qua bờ
                animatePointNew = new Point(435, 345);
                ViewAnimation(3, animatePointNew);
                bapcai.setImageLocation(animatePointNew);
                // Thuyền chạy
                animatePointNew = new Point(400, 323);
                ViewAnimation(4, animatePointNew);
                thuyen.setImageLocation(animatePointNew);
                // Bác lái đò
                animatePointNew = new Point(530, 280);
                ViewAnimation(5, animatePointNew);
                nguoilaido.setImageLocation(animatePointNew);
            }
            
            // Thực hiện kiểm tra xem có thỏa mãn hay không
            if(FunctionEvaluation(buttonSoiBo2, buttonDeBo2, buttonBapcaiBo2, benbo2) == true){ // Thỏa mãn điều kiện 2 con vật không ăn thịt nhau
                endsoiande = false;
                enddeanbapcai = false;
            } 
            else{ // Không thỏa mãn 
                if(buttonSoiBo2 == false && buttonDeBo2 == true && buttonBapcaiBo2 == true){ // Dê ăn bắp cải
                    enddeanbapcai = true;
                    animatePointNew = new Point(960, 210);
                    ViewAnimation(2, animatePointNew);
                }
                if(buttonSoiBo2 == true && buttonDeBo2 == true && buttonBapcaiBo2 == false){ // Sói ăn dê
                    endsoiande = true;
                    animatePointNew = new Point(960, 310);
                    ViewAnimation(1, animatePointNew);
                }
            }
        }
    }//GEN-LAST:event_buttonTraiMouseClicked

    private void jlHuongDanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlHuongDanMouseClicked
        // Cần tắt hết repain đi
        messenger = true;
        repaint();
        // Mở messenger lên
        JPHuongDan.setVisible(true);
    }//GEN-LAST:event_jlHuongDanMouseClicked

    private void jlGioiThieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlGioiThieuMouseClicked
        // Cần tắt hết repain đi
        messenger = true;
        repaint();
        // Mở messenger lên
        JPGioiThieu.setVisible(true);
    }//GEN-LAST:event_jlGioiThieuMouseClicked

    private void jLGioiThieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLGioiThieuMouseClicked
        // Mở repain lên
        messenger = false;
        repaint();
        // Mở messenger lên
        JPGioiThieu.setVisible(false);
    }//GEN-LAST:event_jLGioiThieuMouseClicked

    private void jLHuongDanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLHuongDanMouseClicked
        // Mở repain lên
        messenger = false;
        repaint();
        // Mở messenger lên
        JPHuongDan.setVisible(false);
    }//GEN-LAST:event_jLHuongDanMouseClicked

    private void jLThoatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLThoatMouseClicked
        messenger = true;
        repaint();
        JPTienTrinh.setVisible(true);
    }//GEN-LAST:event_jLThoatMouseClicked

    private void buttonMoDauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMoDauActionPerformed
        JPMoDau.setVisible(false);
        JPLuatChoi.setVisible(true);
    }//GEN-LAST:event_buttonMoDauActionPerformed

    private void btLuatChoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLuatChoiActionPerformed
        JPLuatChoi.setVisible(false);
        if(buttonSoiBo1 == true && buttonDeBo1 == true && buttonBapcaiBo1 == true && trenthuyen == false){
            jPMessangeCartoon.setVisible(true);
        }
        if(reload == true){
            Reload();
        }
        messenger = false;
        repaint();
    }//GEN-LAST:event_btLuatChoiActionPerformed

    private void btThuaCuocTroVeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btThuaCuocTroVeActionPerformed
        reload = true;
        JPThuaCuoc.setVisible(false);
        JPMoDau.setVisible(true);
        
        // Tắt đánh nhau đi
        jbo1soiande.setVisible(false);
        jbo1deanbapcai.setVisible(false);
        jbo2soiande.setVisible(false);
        jbo2deanbapcai.setVisible(false);
    }//GEN-LAST:event_btThuaCuocTroVeActionPerformed

    private void btThuaCuocChoiMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btThuaCuocChoiMoiActionPerformed
        reload = true;
        JPThuaCuoc.setVisible(false);
        JPLuatChoi.setVisible(true);
        
        // Tắt đánh nhau đi
        jbo1soiande.setVisible(false);
        jbo1deanbapcai.setVisible(false);
        jbo2soiande.setVisible(false);
        jbo2deanbapcai.setVisible(false);
    }//GEN-LAST:event_btThuaCuocChoiMoiActionPerformed

    private void btvictoryTroVeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btvictoryTroVeActionPerformed
        reload = true;
        JPChienThang.setVisible(false);
        JPMoDau.setVisible(true);
        
        // Tắt đánh nhau đi
        jbo1soiande.setVisible(false);
        jbo1deanbapcai.setVisible(false);
        jbo2soiande.setVisible(false);
        jbo2deanbapcai.setVisible(false);
    }//GEN-LAST:event_btvictoryTroVeActionPerformed

    private void btVictoryChoiLaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btVictoryChoiLaiActionPerformed
        reload = true;
        JPChienThang.setVisible(false);
        JPLuatChoi.setVisible(true);
        
        // Tắt đánh nhau đi
        jbo1soiande.setVisible(false);
        jbo1deanbapcai.setVisible(false);
        jbo2soiande.setVisible(false);
        jbo2deanbapcai.setVisible(false);
    }//GEN-LAST:event_btVictoryChoiLaiActionPerformed

    private void btTienTrinhHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTienTrinhHuyActionPerformed
        messenger = false;
        repaint();
        JPTienTrinh.setVisible(false);
    }//GEN-LAST:event_btTienTrinhHuyActionPerformed

    private void btTienTrinhTiepTucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTienTrinhTiepTucActionPerformed
        JPTienTrinh.setVisible(false);
        reload = true;
        JPMoDau.setVisible(true);
    }//GEN-LAST:event_btTienTrinhTiepTucActionPerformed

    private void jlLuatChoiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlLuatChoiMouseClicked
        jPMessangeCartoon.setVisible(false);
        messenger = true;
        repaint();
        JPLuatChoi.setVisible(true);
    }//GEN-LAST:event_jlLuatChoiMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JPChienThang;
    private javax.swing.JPanel JPGioiThieu;
    private javax.swing.JPanel JPHuongDan;
    private javax.swing.JPanel JPLuatChoi;
    private javax.swing.JPanel JPMoDau;
    private javax.swing.JPanel JPThuaCuoc;
    private javax.swing.JPanel JPTienTrinh;
    private Ultil.Background background1;
    private Ultil.BackgroundDialog backgroundDialog1;
    private Ultil.BackgroundDialog backgroundDialog2;
    private Ultil.BackgroundHelp backgroundHelp1;
    private Ultil.BackgroundHelp backgroundHelp2;
    private Ultil.BackgroundHelp backgroundHelp3;
    private Ultil.Button btBapcai;
    private Ultil.Button btDe;
    private Ultil.Button btLuatChoi;
    private Ultil.Button btSoi;
    private Ultil.Button btThuaCuocChoiMoi;
    private Ultil.Button btThuaCuocTroVe;
    private Ultil.Button btTienTrinhHuy;
    private Ultil.Button btTienTrinhTiepTuc;
    private Ultil.Button btVictoryChoiLai;
    private Ultil.Button btvictoryTroVe;
    private Ultil.Button buttonMoDau;
    private javax.swing.JLabel buttonPhai;
    private javax.swing.JLabel buttonTrai;
    private javax.swing.JLabel jLGioiThieu;
    private javax.swing.JLabel jLHuongDan;
    private javax.swing.JLabel jLThoat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPMessangeCartoon;
    private javax.swing.JPanel jPMessangeCartoon1;
    private javax.swing.JPanel jPmessenger;
    private javax.swing.JLabel jbo1deanbapcai;
    private javax.swing.JLabel jbo1soiande;
    private javax.swing.JLabel jbo2deanbapcai;
    private javax.swing.JLabel jbo2soiande;
    private javax.swing.JLabel jlGioiThieu;
    private javax.swing.JLabel jlHuongDan;
    private javax.swing.JLabel jlLuatChoi;
    private javax.swing.JLabel jthongbao;
    private javax.swing.JLabel jthongbaothuacuoc;
    private javax.swing.JLabel jthongbaothuacuoc1;
    // End of variables declaration//GEN-END:variables
}
