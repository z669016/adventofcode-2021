package com.putoet.day19;

import com.putoet.grid.Point3D;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Scanners {
    private Scanners() {}

    public static List<Scanner> of(List<String> lines) {
        final List<Scanner> scanners = new ArrayList<>();
        int scannerId = 0;
        for (int offset = 0; offset < lines.size(); offset++) {
            final String scannerIdLine = lines.get(offset);
            offset++;

            final Set<Point3D> beacons = new HashSet<>();
            while(offset < lines.size() && lines.get(offset).length() > 0) {
                beacons.add(point3d(lines.get(offset)));
                offset++;
            }
            scanners.add(new Scanner(scannerId,beacons));
            scannerId++;
        }

        return scanners;
    }

    private static Point3D point3d(String line) {
        assert line != null;

        final String[] split = line.split(",");
        assert split.length == 3;

        return Point3D.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
    }
}
