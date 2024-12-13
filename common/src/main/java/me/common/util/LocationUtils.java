/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.common.util;

public class LocationUtils {

    public static double distance(double latitude1, double longitude1, double latitude2, double longitude2) {
        return distance(latitude1, longitude1, latitude2, longitude2, 'K');
    }

    /*
    @unit   K: Kilometers - M: Miles - N: Nautical Miles
    */
    public static double distance(double latitude1, double longitude1, double latitude2, double longitude2, char unit) {
        double theta = longitude1 - longitude2;
        double dist = Math.sin(deg2rad(latitude1)) * Math.sin(deg2rad(latitude2)) + Math.cos(deg2rad(latitude1)) * Math.cos(deg2rad(latitude2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }

        return (dist);
    }

    //converts decimal degrees to radians
    public static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    //converts radians to decimal degrees
    public static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
