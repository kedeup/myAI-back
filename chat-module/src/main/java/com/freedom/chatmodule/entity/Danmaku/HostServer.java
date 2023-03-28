package com.freedom.chatmodule.entity.Danmaku;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author kede·W  on  2023/3/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HostServer {
    private String host;
    private Integer port;
    private Integer ws_port;
    private Integer wss_port;
}


/*
使用 Lombok 提供的 `@Data` 注解可以自动生成 Java 类中常用的方法，例如 `toString()`、`equals()`、`hashCode()` 等。因此，在使用 `@Data` 注解后通常不需要手动重写这些方法。

使用 `@Data` 注解可以大大简化 Java 类的开发，同时也减少了代码的冗余。但是，请注意在使用 `@Data` 注解时，必须确保所有属性都是正确地实现了 `equals()` 和 `hashCode()` 方法。否则，在使用 `@Data` 注解后可能会出现一些奇怪的问题，例如对象无法正确地放入 HashSet 或者 HashMap 中等。

因此，虽然使用 `@Data` 注解可以避免手动编写 `toString()`、`equals()`、`hashCode()` 等方法，但是在使用过程中也要特别注意其应用场景和使用规范。
*/