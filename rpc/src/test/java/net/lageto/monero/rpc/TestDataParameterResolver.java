/*
 * Copyright (c) 2021 Lageto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.lageto.monero.rpc;

import net.lageto.monero.rpc.model.BlockHeader;
import net.lageto.monero.rpc.model.BlockTemplate;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class TestDataParameterResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(BlockHeader.class)
                || parameterContext.getParameter().getType().equals(BlockTemplate.class)
                || parameterContext.getParameter().isAnnotationPresent(WalletAddress.class);
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
        } else if (parameterContext.getParameter().getType().equals(BlockTemplate.class)) {
            return new BlockTemplate(
                    "0e0ebef0c687060c3b9921087534971b9d26e268cdbc3f000b22b44c446a5bbbc05486a76b0ddc0000000" +
                            "0c9819efbeb751068670787df7f191b1099ef3d53cc0353ad2b3e38d575712a4a19",
                    "0e0ebef0c687060c3b9921087534971b9d26e268cdbc3f000b22b44c446a5bbbc05486a76b0ddc0000000002b4" +
                            "ee920101fff8ed920101d3dbc7bdb11b02aae2ff3bb337cb735af8e2dbe57d64f8608d24b2807768fd63b91" +
                            "2505a497ca35f018b4440282cd6b70dbb13bd8c08c1936b796cb8c9e4703d8455d6270c15683e04023c0000" +
                            "000000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
                            "00000000000000000000000000000001883eefc5a4e9c157aeb083e2e0d77b7196009624a6141f47229da75" +
                            "b5b606fb63e85d422745e0589856591689da18444760612d7da73b9a8de742a19b83eeb210b6f7579c66881" +
                            "c89154930343a0abca57efcbcfcfc74a4c97691c46ce94b9677a0af55e4764aeac42e6c9021ad4c030a3e0b" +
                            "ba177b2aa6a902fe3308a521f2a44ac0ced75a5540dbabb352f941d87c7ac9c78f2b04b3e4465b5aa7240b6" +
                            "c3b78fcd8393f0a4f016687e93ee3fd70c4062a830fc0d08de113d18767eeec42e044aa894e7b58d3c550fc" +
                            "35f38d13be8da7640ca71333a5d7a78f563f35ad232f399bb4240fbc80f3c1b63252188a7f281af27f49230" +
                            "e5c182773c6ad711e5324717824dec7c9b634c04ec99f14d27fe561b2deb1b46e8c7fc5a42656b141325195" +
                            "cb0486c9d249e1ccddc82fb165dadef544248e2a84d3fc67704911f493a5936e1491c7f3692b76591ee4ec6" +
                            "42e210f00ee0f076cca9fb2bae4748cc367b8b9216b7a6c250b28fd74d0bb23751f6cc29cbef9d91d0651f3" +
                            "1eba43451aef0fe882528efbb5589f0c557f82481393026f65bdf194170240f3349ad39fa9b2b66bb74e533" +
                            "7d9c5a431519b12b0dbd552b5f3a4419b3a30087230d546fff13b104d71735670c78a77dbc618b1a66f8b55" +
                            "ff71f1124b065a665ab3fdaf5625c137e2ccb6744f5e6e54aa4dc449e917d5b93e14795a4df6e90735908a2" +
                            "670693eeaacc8cc52d6a6c3e932c7bf1ef5c2dbc8bbc967583705d4bd1b005b86a9f5c39589cde67b7db88e" +
                            "9c4ff5e9b3830c2c8e4051441d62c8437c2153f1cf48fb8a7069f97108e061e0c4f8f3600f93f3ad19a30b4" +
                            "3b2b9dd13c593e9a6187dff1eebcefd8f89026670c55de1809240d76b84f42cd2a2f9a71c00f7542fd27b5a" +
                            "8bbe74caeccc73052f7744c5646d83b27d4fef5cede2e9395e9c8c2065396dab7bc7f422cab049a57efbb54" +
                            "c105acbeb3c7328d00649f35ac3c3f15c7c28e8789bcc97d7d7296d72a11a335bd481cd7c51157e86520119" +
                            "c277d4f56d1f1a62ec7f631751ca0dc417e6e0cecc045704e1f26570f9902cffc60a1eb52a0e654da0b6ca9" +
                            "df2",
                    296465821727L,
                    940995374547L,
                    2406136L,
                    "",
                    "0c3b9921087534971b9d26e268cdbc3f000b22b44c446a5bbbc05486a76b0ddc",
                    130,
                    "331da8a7a190a2cfc13c2195b6d6213dc4ce487a22b806a5957673a7ef3df0",
                    2404352L
            );
        } else if (parameterContext.getParameter().isAnnotationPresent(WalletAddress.class)) {
            return "498WwTT53cGQg8hhQF2SgDGFFyaLmtfMG8vx76ST4ouGSXWUmtiQniq2P5ksrZF6cFQPiAKqvUdBkQMUjT5W8Spy3D2D3ZV";
        }

        throw new IllegalArgumentException();
    }
}
