package org.apache.commons.text.lookup;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes6.dex */
abstract class AbstractPathFencedLookup extends AbstractStringLookup {
    protected final List<Path> fences;

    AbstractPathFencedLookup(Path... pathArr) {
        this.fences = pathArr != null ? (List) Arrays.stream(pathArr).map(new Function() { // from class: org.apache.commons.text.lookup.AbstractPathFencedLookup$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Path) obj).toAbsolutePath();
            }
        }).collect(Collectors.toList()) : Collections.emptyList();
    }

    protected Path getPath(String str) {
        Path path = Paths.get(str, new String[0]);
        if (!this.fences.isEmpty()) {
            final Path absolutePath = path.normalize().toAbsolutePath();
            Stream<Path> stream = this.fences.stream();
            Objects.requireNonNull(absolutePath);
            if (!stream.filter(new Predicate() { // from class: org.apache.commons.text.lookup.AbstractPathFencedLookup$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return absolutePath.startsWith((Path) obj);
                }
            }).findFirst().isPresent()) {
                throw IllegalArgumentExceptions.format("[%s] -> [%s] not in %s", str, absolutePath, this.fences);
            }
        }
        return path;
    }
}
