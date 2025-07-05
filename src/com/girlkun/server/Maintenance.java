package com.girlkun.server;

import com.girlkun.services.Service;
import com.girlkun.utils.Logger;


public class Maintenance extends Thread {

    public static boolean isRuning = false;
    public boolean canUseCode;
    private static Maintenance i;

    private int min;
    
    // Thêm biến để theo dõi nguyên nhân bảo trì
    private String maintenanceReason = "Unknown";

    private Maintenance() {

    }

    public static Maintenance gI() {
        if (i == null) {
            i = new Maintenance();
        }
        return i;
    }

    public void start(int min) {
        this.start(min, "Manual maintenance");
    }
    
    public void start(int min, String reason) {
        if (!isRuning) {
            this.maintenanceReason = reason;
            isRuning = true;
            this.min = min;
            this.start();
        }
    }

    @Override
    public void run() {
        while (this.min > 0) {
            this.min--;
            Service.gI().sendThongBaoAllPlayer("Hệ thống sẽ bảo trì sau " + min
                    + " giây nữa, vui lòng thoát game để tránh mất vật phẩm");
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.err.println("[ERROR-MAINTENANCE] Lỗi khi sleep: " + e.getMessage());
            }
          
        }
        Logger.error("...........................................\n");
        System.out.println("[DEBUG-MAINTENANCE] Kết thúc quá trình bảo trì, đóng server");
        ServerManager.gI().close(100);
    }

}
