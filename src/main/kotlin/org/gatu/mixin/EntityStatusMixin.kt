package org.gatu.mixin

import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayNetworkHandler
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket
import net.minecraft.sound.SoundCategory
import org.gatu.sound.PopSoundManager
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(ClientPlayNetworkHandler::class)
class EntityStatusMixin {

    @Inject(method = ["onEntityStatus"], at = [At("HEAD")], cancellable = true)
    private fun onPop(packet: EntityStatusS2CPacket, ci: CallbackInfo) {

        if (packet.status.toInt() == 35) {

            ci.cancel()

            val client = MinecraftClient.getInstance()
            val world = client.world ?: return
            val entity = packet.getEntity(world) ?: return

            world.playSound(
                client.player,
                entity.blockPos,
                PopSoundManager.currentSound,
                SoundCategory.PLAYERS,
                100.0f,
                1.0f
            )
        }
    }
}