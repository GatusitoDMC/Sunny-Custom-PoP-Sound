package org.gatu.commands

import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import org.gatu.sound.PopSoundManager
import java.util.concurrent.CompletableFuture

object PoPSoundsCommand {

    private val sounds = listOf("tnt", "xp", "anvil", "totem")

    fun register() {
        ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ ->

            dispatcher.register(
                ClientCommandManager.literal("popsound")
                    .then(
                        ClientCommandManager.argument("sound", StringArgumentType.word())
                            .suggests { _, builder: SuggestionsBuilder ->
                                sounds.forEach { builder.suggest(it) }
                                builder.buildFuture()
                            }
                            .executes { context ->

                                val soundName = StringArgumentType.getString(context, "sound")
                                val client = MinecraftClient.getInstance()

                                PopSoundManager.currentSound = when (soundName.lowercase()) {
                                    "tnt" -> SoundEvents.ENTITY_TNT_PRIMED
                                    "xp" -> SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP
                                    "anvil" -> SoundEvents.BLOCK_ANVIL_LAND
                                    "totem" -> SoundEvents.ITEM_TOTEM_USE
                                    else -> {
                                        client.player?.sendMessage(Text.literal("Sonido no válido"), false)
                                        return@executes 0
                                    }
                                }

                                client.player?.sendMessage(
                                    Text.literal("Selected: $soundName"),
                                    false
                                )

                                1
                            }
                    )
            )
        }
    }
}