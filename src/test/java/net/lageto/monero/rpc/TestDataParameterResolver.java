package net.lageto.monero.rpc;

import net.lageto.monero.rpc.model.BlockHeader;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class TestDataParameterResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(BlockHeader.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        Class<?> clazz = parameterContext.getParameter().getType();

        if (clazz.equals(BlockHeader.class)) {
            return new BlockHeader(
                    128172,
                    10,
                    322104577336L,
                    "3971f36b9004a6568e1ad0fab1dc0c019ce14c90c9cd62f64594b48e8cd7a6d6",
                    2404765L,
                    14,
                    14,
                    3791687207L,
                    false,
                    "68800e6112a112e5fb7723665972140c041938c4cfd01c1774d735048330fcc1",
                    949159500346L,
                    1626285630L
            );
        }

        throw new IllegalArgumentException();
    }
}
