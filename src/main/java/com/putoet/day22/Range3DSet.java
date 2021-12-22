package com.putoet.day22;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Range3DSet {
    private final List<Range3D> included = new ArrayList<>();
    private final List<Range3D> excluded = new ArrayList<>();

    public Range3DSet() {
    }

    public Range3DSet(List<Range3D> included) {
        this.included.addAll(included);
    }

    public Range3DSet(List<Range3D> included, List<Range3D> excluded) {
        this.included.addAll(included);
        this.excluded.addAll(excluded);
    }

    public Range3DSet add(Range3D range) {
        if (included.isEmpty()) {
            included.add(range);
            return this;
        }

        boolean foundOverlap = true;
        while (foundOverlap) {
            foundOverlap = false;

            for (int idx = 0; idx < included.size(); idx++) {
                final Range3D current = included.get(idx);
                if (current.contains(range))
                    continue;

                if (current.overlap(range)) {
                    included.add(range);
                    final Optional<Range3D> exclude = current.carve(range);
                    exclude.ifPresent(excluded::add);
                }
            }
        }

        return this;
    }
}
