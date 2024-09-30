package net.archers.item;

import net.archers.ArchersMod;
import net.archers.item.armor.ArcherArmor;
import net.archers.util.SoundHelper;
import net.fabric_extras.ranged_weapon.api.EntityAttributes_RangedWeapon;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.spell_engine.api.item.ItemConfig;
import net.spell_engine.api.item.armor.Armor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class Armors {
    public static RegistryEntry<ArmorMaterial> material(String name,
                                                        int protectionHead, int protectionChest, int protectionLegs, int protectionFeet,
                                                        int enchantability, RegistryEntry<SoundEvent> equipSound, Supplier<Ingredient> repairIngredient) {
        var material = new ArmorMaterial(
                Map.of(
                        ArmorItem.Type.HELMET, protectionHead,
                        ArmorItem.Type.CHESTPLATE, protectionChest,
                        ArmorItem.Type.LEGGINGS, protectionLegs,
                        ArmorItem.Type.BOOTS, protectionFeet),
                enchantability, equipSound, repairIngredient,
                List.of(new ArmorMaterial.Layer(Identifier.of(ArchersMod.ID, name))),
                0,0
        );
        return Registry.registerReference(Registries.ARMOR_MATERIAL, Identifier.of(ArchersMod.ID, name), material);
    }

    public static RegistryEntry<ArmorMaterial> material_t1 = material(
            "archer_armor",
            2, 3, 3, 2,
            9,
            SoundHelper.ARCHER_ARMOR_EQUIP.entry(), () -> { return Ingredient.ofItems(Items.LEATHER); });

    public static RegistryEntry<ArmorMaterial> material_t2 = material(
            "ranger_armor",
            2, 3, 3, 2,
            10,
            SoundHelper.ARCHER_ARMOR_EQUIP.entry(), () -> { return Ingredient.ofItems(Items.TURTLE_SCUTE); });

    public static RegistryEntry<ArmorMaterial> material_t3 = material(
            "netherite_ranger_armor",
            2, 3, 3, 2,
            15,
            SoundHelper.ARCHER_ARMOR_EQUIP.entry(), () -> { return Ingredient.ofItems(Items.NETHERITE_INGOT); });


    public static final ArrayList<Armor.Entry> entries = new ArrayList<>();
    private static Armor.Entry create(RegistryEntry<ArmorMaterial> material, Identifier id, int durability, Armor.Set.ItemFactory factory, ItemConfig.ArmorSet defaults) {
        var entry = Armor.Entry.create(
                material,
                id,
                durability,
                factory,
                defaults);
        entries.add(entry);
        return entry;
    }

    private static ItemConfig.Attribute damageMultiplier(float value) {
        return new ItemConfig.Attribute(
                EntityAttributes_RangedWeapon.DAMAGE.id.toString(),
                value,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE);
    }

    private static ItemConfig.Attribute hasteMultiplier(float value) {
        return new ItemConfig.Attribute(
                EntityAttributes_RangedWeapon.HASTE.id.toString(),
                value,
                EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE);
    }

    public static final float damage_T1 = 0.05F;
    public static final float haste_T2 = 0.03F;
    public static final float damage_T2 = 0.08F;
    public static final float haste_T3 = 0.05F;
    public static final float damage_T3 = 0.1F;

    public static final Armor.Set archerArmorSet_T1 = create(
            material_t1,
            Identifier.of(ArchersMod.ID, "archer_armor"),
            15,
            ArcherArmor::archer,
            ItemConfig.ArmorSet.with(
                    new ItemConfig.ArmorSet.Piece(2)
                            .add(damageMultiplier(damage_T1)),
                    new ItemConfig.ArmorSet.Piece(3)
                            .add(damageMultiplier(damage_T1)),
                    new ItemConfig.ArmorSet.Piece(3)
                            .add(damageMultiplier(damage_T1)),
                    new ItemConfig.ArmorSet.Piece(2)
                            .add(damageMultiplier(damage_T1))
            ))
            .armorSet();

    public static final Armor.Set archerArmorSet_T2 = create(
            material_t2,
            Identifier.of(ArchersMod.ID, "ranger_armor"),
            25,
            ArcherArmor::ranger,
            ItemConfig.ArmorSet.with(
                    new ItemConfig.ArmorSet.Piece(2)
                            .add(damageMultiplier(damage_T2))
                            .add(hasteMultiplier(haste_T2)),
                    new ItemConfig.ArmorSet.Piece(3)
                            .add(damageMultiplier(damage_T2))
                            .add(hasteMultiplier(haste_T2)),
                    new ItemConfig.ArmorSet.Piece(3)
                            .add(damageMultiplier(damage_T2))
                            .add(hasteMultiplier(haste_T2)),
                    new ItemConfig.ArmorSet.Piece(2)
                            .add(damageMultiplier(damage_T2))
                            .add(hasteMultiplier(haste_T2))
            ))
            .armorSet();

    public static final Armor.Set archerArmorSet_T3 = create(
            material_t3,
            Identifier.of(ArchersMod.ID, "netherite_ranger_armor"),
            35,
            ArcherArmor::ranger,
            ItemConfig.ArmorSet.with(
                    new ItemConfig.ArmorSet.Piece(2)
                            .add(damageMultiplier(damage_T3))
                            .add(hasteMultiplier(haste_T3)),
                    new ItemConfig.ArmorSet.Piece(3)
                            .add(damageMultiplier(damage_T3))
                            .add(hasteMultiplier(haste_T3)),
                    new ItemConfig.ArmorSet.Piece(3)
                            .add(damageMultiplier(damage_T3))
                            .add(hasteMultiplier(haste_T3)),
                    new ItemConfig.ArmorSet.Piece(2)
                            .add(damageMultiplier(damage_T3))
                            .add(hasteMultiplier(haste_T3))
            ))
            .armorSet();

    public static void register(Map<String, ItemConfig.ArmorSet> configs) {
        Armor.register(configs, entries, Group.KEY);
    }
}

