package org.gatu

import net.fabricmc.api.ModInitializer
import org.gatu.commands.PoPSoundsCommand
import org.slf4j.LoggerFactory

object PoPSound : ModInitializer {
    private val logger = LoggerFactory.getLogger("pop-sound")

	override fun onInitialize() {

		logger.info("By GatusitoDMC")
		PoPSoundsCommand.register()
	}
}