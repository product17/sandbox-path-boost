package io.sandbox.path;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.sandbox.path.effects.PathStatusEffect;

public class Main implements ModInitializer {
	public static final String modId = "path";
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LogManager.getLogger(modId);

	public static final StatusEffect PATH = new PathStatusEffect();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		Registry.register(Registry.STATUS_EFFECT, new Identifier(modId, "boost"), PATH.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "16976b60-675e-11ec-90d6-0242ac120003", 0.20000000298023224D, Operation.MULTIPLY_TOTAL));

		LOGGER.info("Loaded mod: " + modId);
	}
}
