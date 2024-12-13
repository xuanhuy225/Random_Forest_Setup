package me.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

public class IPRangeChecker {

    private static final Logger LOG = LoggerFactory.getLogger(IPRangeChecker.class);

    public static boolean isIPAllowed(String clientIp, String allowedIP) {
        try {
            InetAddress ipAddress = InetAddress.getByName(clientIp);
            return isIpAllowed(ipAddress, allowedIP);

        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
            return false;
        }

    }

    public static boolean isIPAllowed(String clientIp, List<String> allowedIPs) {
        try {
            InetAddress ipAddress = InetAddress.getByName(clientIp);
            for (String allowedIP : allowedIPs) {
                boolean ipAllowed = isIpAllowed(ipAddress, allowedIP);
                if (ipAllowed) {
                    return ipAllowed;
                }
            }
            return false;
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
            return false;
        }

    }

    private static boolean isIpAllowed(InetAddress ipAddress, String allowedIP) throws UnknownHostException {
        try {
            SubnetMask subnetMask = new SubnetMask(allowedIP);

            if (subnetMask.isSingleIP()) {
                if (subnetMask.contains(ipAddress)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if (subnetMask.contains(ipAddress)) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
            return false;
        }
    }

}

class SubnetMask {

    private final boolean isSingleIP;
    private final int prefixLength;
    private final InetAddress subnetMask;

    public SubnetMask(String subnetMaskString) throws UnknownHostException {
        String[] parts = subnetMaskString.split("/");
        if (parts.length == 1 || parts[1].equals("32")) {
            this.isSingleIP = true;
            this.prefixLength = 32;
            this.subnetMask = InetAddress.getByName(parts[0]);
        } else {
            this.isSingleIP = false;
            this.prefixLength = Integer.parseInt(parts[1]);
            this.subnetMask = InetAddress.getByName(parts[0]);
        }
    }

    public boolean isSingleIP() {
        return isSingleIP;
    }

    public boolean contains(InetAddress ipAddress) {
        if (isSingleIP) {
            return subnetMask.equals(ipAddress);
        } else {
            byte[] address = ipAddress.getAddress();
            byte[] mask = subnetMask.getAddress();
            int bits = prefixLength;
            int index = 0;
            while (bits >= 8) {
                if (address[index] != mask[index]) {
                    return false;
                }
                bits -= 8;
                index++;
            }
            if (bits > 0) {
                int maskByte = (0xFF << (8 - bits)) & 0xFF;
                if ((address[index] & maskByte) != (mask[index] & maskByte)) {
                    return false;
                }
            }
            return true;
        }
    }

}