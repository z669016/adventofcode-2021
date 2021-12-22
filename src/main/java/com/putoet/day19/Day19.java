package com.putoet.day19;

import com.putoet.day.Day;
import com.putoet.grid.Point3D;
import com.putoet.resources.ResourceLines;

import java.util.*;

public class Day19 extends Day {
    private final List<String> input = ResourceLines.list("/day19.txt");
    private final List<Scanner> scanners = new ArrayList<>(Scanners.of(ResourceLines.list("/day19.txt")));
    private List<Scanner> transformed;

    protected Day19(String[] args) {
        super(args);
    }

    public static void main(String[] args) {
        final Day day = new Day19(args);
        day.challenge();
    }

    @Override
    public void part1() {
        final Scanner base = scanners.remove(0);
        final List<Point3D> scannerLocations = new ArrayList<>(Collections.singleton(Point3D.ORIGIN));
        while (scanners.size() > 0) {
            System.out.println("pool size:" + scanners.size());
            long time = System.currentTimeMillis();
            outer: for (Scanner scanner : scanners) {
                for (List<Point3D> rotation : scanner.rotations()) {
                    final Optional<Point3D> transformation = base.findTranslation(rotation);
                    if (transformation.isPresent()) {
                        scannerLocations.add(transformation.get());
//                        base.add(rotation, new Scanner(scanner.id(), transformation));
//                        scanners.remove(scanner);
                        break outer;
                    }
                }
            }
            System.out.println("ms:" + (System.currentTimeMillis() - time));
        }
        System.out.println("part1:" + base.coords.size());
    }
}
