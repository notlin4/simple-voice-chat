package de.maxhenkel.voicechat.integration.commodore;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import de.maxhenkel.voicechat.Voicechat;
import de.maxhenkel.voicechat.command.VoiceChatCommands;
import me.lucko.commodore.Commodore;

public class CommodoreCommands {

    public static void registerCompletions(Commodore commodore) {
        LiteralArgumentBuilder<?> literalBuilder = LiteralArgumentBuilder.literal(VoiceChatCommands.VOICECHAT_COMMAND)
                .then(LiteralArgumentBuilder.literal("help"))
                .then(LiteralArgumentBuilder.literal("test").then(RequiredArgumentBuilder.argument("target", playerArgument())))
                .then(LiteralArgumentBuilder.literal("invite").then(RequiredArgumentBuilder.argument("target", playerArgument())))
                .then(LiteralArgumentBuilder.literal("join").then(RequiredArgumentBuilder.argument("group", uuidArgument())))
                .then(LiteralArgumentBuilder.literal("join").then(RequiredArgumentBuilder.argument("group", uuidArgument()).then(RequiredArgumentBuilder.argument("password", StringArgumentType.string()))))
                .then(LiteralArgumentBuilder.literal("leave"));

        commodore.register(literalBuilder);
    }

    private static ArgumentType<?> playerArgument() {
        return Voicechat.compatibility.playerArgument();
    }

    private static ArgumentType<?> uuidArgument() {
        return Voicechat.compatibility.uuidArgument();
    }

}
