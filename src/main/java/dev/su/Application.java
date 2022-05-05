package dev.su;

import com.google.protobuf.DescriptorProtos;
import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.util.stream.Collectors;

public class Application {

    @SneakyThrows
    public static void main(String[] args) {
        DescriptorProtos.FileDescriptorSet fd = DescriptorProtos.FileDescriptorSet
                .parseFrom(new FileInputStream("examples/descriptors/event.desc"));

        DescriptorProtos.DescriptorProto desc = fd.getFile(0).getMessageType(0);

        System.out.println(desc.getFieldCount());
        System.out.println(desc.getFieldList()
                .stream()
                .map(a -> a.getType().getDescriptorForType().toString())
                .collect(Collectors.joining(", "))
        );

    }
}
