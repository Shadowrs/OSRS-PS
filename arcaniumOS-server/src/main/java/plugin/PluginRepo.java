package plugin;

import org.arcanium.plugin.Plugin;
import plugin.combat.AttackOptionPlugin;
import plugin.combat.special.AncientMaceSpecialHandler;
import plugin.combat.special.EnergyDrainSpecialHandler;
import plugin.combat.special.ExcaliburSpecialHandler;
import plugin.combat.special.ImpaleSpecialHandler;
import plugin.combat.special.PowershotSpecialHandler;
import plugin.combat.special.PowerstabSpecialHandler;
import plugin.combat.special.PunctureSpecialHandler;
import plugin.combat.special.QuickSmashSpecialHandler;
import plugin.combat.special.RampageSpecialHandler;
import plugin.combat.special.SaradominsLightningHandler;
import plugin.combat.special.SeercullSpecialHandler;
import plugin.combat.special.SeverSpecialHandler;
import plugin.combat.special.SnapshotSpecialHandler;
import plugin.combat.special.SweepSpecialHandler;
import plugin.combat.special.WarstrikeSpecialHandler;
import plugin.combat.spells.AirSpell;
import plugin.combat.spells.BindSpell;
import plugin.dialogue.FaridDialoguePlugin;
import plugin.dialogue.HansDialoguePlugin;
import plugin.dialogue.HerbloreDialogue;
import plugin.dialogue.MakeoverMageDialogue;
import plugin.dialogue.ManDialoguePlugin;
import plugin.interaction.inter.CombatTabInterface;
import plugin.interaction.inter.EquipmentInterface;
import plugin.interaction.inter.MagicBookInterface;
import plugin.interaction.item.BirdNestPlugin;
import plugin.interaction.item.ConsumableOptionPlugin;
import plugin.interaction.item.DiangoItemPlugin;
import plugin.interaction.item.EnchantSpell;
import plugin.interaction.item.MysteryBoxPlugin;
import plugin.interaction.item.PickupPlugin;
import plugin.interaction.item.QuestCapePlugin;
import plugin.interaction.item.TeleTabsOptionPlugin;
import plugin.interaction.object.BankBoothPlugin;
import plugin.interaction.object.DoorManagingPlugin;
import plugin.interaction.object.GrandExchangeShortcut;
import plugin.interaction.object.WildernessDitchPlugin;
import plugin.interaction.player.FollowOptionPlugin;
import plugin.npc.AlKharidWarriorNPC;
import plugin.npc.AntonNPC;
import plugin.npc.ArmourSuitNPC;
import plugin.npc.BarbarianNPC;
import plugin.npc.BennyNPC;
import plugin.npc.CallistoNPC;
import plugin.npc.CatableponNPC;
import plugin.npc.ChaosElementalNPC;
import plugin.npc.ChromaticDragonNPC;
import plugin.npc.CitizenNPC;
import plugin.npc.CowNPC;
import plugin.npc.DarkWizardNPC;
import plugin.npc.DraynorTreeNPC;
import plugin.npc.DuckNPC;
import plugin.npc.ElementalWizardNPC;
import plugin.npc.FishingNPC;
import plugin.npc.GiantMoleNPC;
import plugin.npc.GoblinVillageNPC;
import plugin.npc.GuardNPC;
import plugin.npc.HansNPC;
import plugin.npc.HighwayManNPC;
import plugin.npc.JogreNPC;
import plugin.npc.KalphiteQueenNPC;
import plugin.npc.KamfreenaNPC;
import plugin.npc.KingBlackDragonNPC;
import plugin.npc.LumberKittenNPC;
import plugin.npc.LumbridgeInstructorNPC;
import plugin.npc.ManNPC;
import plugin.interaction.object.StaircaseManagingPlugin;
import plugin.npc.MetalDragonNPC;
import plugin.npc.MonkNPC;
import plugin.npc.NPCTalkPlugin;
import plugin.npc.RatNPC;
import plugin.npc.RockCrabNPC;
import plugin.npc.ShanomiNPC;
import plugin.npc.SheepNPC;
import plugin.npc.ShopNPC;
import plugin.npc.SkippyNPC;
import plugin.npc.StrayDogNPC;
import plugin.npc.ThrowerTrollNPC;
import plugin.npc.TribesmanNPC;
import plugin.npc.TutorialChickenNPC;
import plugin.npc.TutorialRatNPC;
import plugin.npc.VampireSlayerNPC;
import plugin.npc.VenenatisNPC;
import plugin.npc.WerewolfNPC;
import plugin.skills.cooking.CookingPlugin;
import plugin.skills.firemaking.FiremakingPlugin;
import plugin.skills.fletching.FletchingPlugin;
import plugin.skills.herblore.BarbarianMixPlugin;
import plugin.skills.herblore.FinishedPotionPlugin;
import plugin.skills.herblore.GrindItemPlugin;
import plugin.skills.herblore.HerbCleanPlugin;
import plugin.skills.herblore.HerbTarPlugin;
import plugin.skills.herblore.MakeSuperCombatPotionPlugin;
import plugin.skills.herblore.PotionDecantingPlugin;
import plugin.skills.herblore.UnfinishedPotionPlugin;
import plugin.skills.magic.ChargeOrbSpells;
import plugin.skills.magic.ChargeSpell;
import plugin.skills.magic.EnchantCrossbowSpell;
import plugin.skills.magic.ModernTeleportPlugin;
import plugin.skills.magic.SuperheatSpell;
import plugin.skills.magic.TeleotherSpells;
import plugin.skills.mining.MiningMinePlugin;
import plugin.skills.prayer.BoneBuryingOptionPlugin;
import plugin.skills.runecrafting.CombinationRunePlugin;
import plugin.skills.runecrafting.EnchantTiaraPlugin;
import plugin.skills.runecrafting.MysteriousRuinPlugin;
import plugin.skills.runecrafting.RunePouchPlugin;
import plugin.skills.runecrafting.RunecraftingPlugin;
import plugin.skills.runecrafting.TiaraPlugin;
import plugin.skills.runecrafting.abyss.AbyssPlugin;
import plugin.skills.runecrafting.abyss.AbyssalNPC;
import plugin.skills.runecrafting.abyss.DarkMageDialogue;
import plugin.skills.runecrafting.abyss.ZamorakMageDialogue;
import plugin.skills.thieving.PickpocketNPCPlugin;
import plugin.skills.woodcutting.ChopPlugin;
import plugin.tutorial.BrotherBraceDialogue;
import plugin.tutorial.CombatInstructorDialogue;
import plugin.tutorial.FinancialAdvisorDialogue;
import plugin.tutorial.MasterChefDialogue;
import plugin.tutorial.MiningInstructorDialogue;
import plugin.tutorial.QuestGuideDialogue;
import plugin.tutorial.RSGuideDialogue;
import plugin.tutorial.SurvivalExpertDialogue;
import plugin.tutorial.TutorialBook;
import plugin.tutorial.TutorialCompletionDialogue;
import plugin.tutorial.TutorialIslandPlugin;

import java.util.ArrayList;

public class PluginRepo {

    /*
    This is where plugins are defined. Plugins not listed here will NOT be loaded.
     */

    public static final ArrayList<Plugin> combatPluginRepo = new ArrayList<>();
    public static final ArrayList<Plugin> dialoguePluginRepo = new ArrayList<>();
    public static final ArrayList<Plugin> interactionPluginRepo = new ArrayList<>();
    public static final ArrayList<Plugin> NPCPluginRepo = new ArrayList<>();
    public static final ArrayList<Plugin> skillPluginRepo = new ArrayList<>();
    public static final ArrayList<Plugin> tutorialPluginRepo = new ArrayList<>();

    /*
    Combat
     */

    public static final Plugin attackOption = new AttackOptionPlugin();

    //Specials

    public static final Plugin ancientMaceSpec = new AncientMaceSpecialHandler();
    public static final Plugin energyDrainSpec = new EnergyDrainSpecialHandler();
    public static final Plugin excaliburSpec = new ExcaliburSpecialHandler();
    public static final Plugin impaleSpec = new ImpaleSpecialHandler();
    public static final Plugin powershotSpec = new PowershotSpecialHandler();
    public static final Plugin powerstabSpec = new PowerstabSpecialHandler();
    public static final Plugin punctureSpec = new PunctureSpecialHandler();
    public static final Plugin quickSmashSpec = new QuickSmashSpecialHandler();
    public static final Plugin rampageSpec = new RampageSpecialHandler();
    public static final Plugin saradominsLightningSpec = new SaradominsLightningHandler();
    public static final Plugin seercullSpec = new SeercullSpecialHandler();
    public static final Plugin severSpec = new SeverSpecialHandler();
    public static final Plugin snapshotSpec = new SnapshotSpecialHandler();
    public static final Plugin sweepSpec = new SweepSpecialHandler();
    public static final Plugin warstrikeSpec = new WarstrikeSpecialHandler();

    //Spells

    public static final Plugin airSpell = new AirSpell();
    public static final Plugin bindSpell = new BindSpell();

    /*
    Dialogue
     */

    public static final Plugin faridDialogue = new FaridDialoguePlugin();
    public static final Plugin hansDialogue = new HansDialoguePlugin();
    public static final Plugin herbloreDialogue = new HerbloreDialogue();
    public static final Plugin makeoverMageDialogue = new MakeoverMageDialogue();
    public static final Plugin manDialogue = new ManDialoguePlugin();

    /*
    Interaction
     */

    //Interfaces

    public static final Plugin combatTab = new CombatTabInterface();
    public static final Plugin equipment = new EquipmentInterface();
    public static final Plugin magicBook = new MagicBookInterface();

    //Items

    public static final Plugin birdNest = new BirdNestPlugin();
    public static final Plugin consumableOption = new ConsumableOptionPlugin();
    public static final Plugin diangoItem = new DiangoItemPlugin();
    public static final Plugin enchantSpell = new EnchantSpell();
    public static final Plugin mysteryBox = new MysteryBoxPlugin();
    public static final Plugin pickup = new PickupPlugin();
    public static final Plugin questCape = new QuestCapePlugin();
    public static final Plugin teleTabsOption = new TeleTabsOptionPlugin();

    //Objects

    //public static final Plugin dwarfCannonHandler = new DwarfCannonHandler();
    //public static final Plugin dwarfCannonRevolution = new DwarfCannonRevoltion();
    public static final Plugin bankBooth = new BankBoothPlugin();
    public static final Plugin doorManaging = new DoorManagingPlugin();
    public static final Plugin grandExchangeShortcut = new GrandExchangeShortcut();
    public static final Plugin staircaseManaging = new StaircaseManagingPlugin();
    public static final Plugin wildernessDitch = new WildernessDitchPlugin();

    //Player

    public static final Plugin followOption = new FollowOptionPlugin();

    /*
    NPCs
     */

    //TODO: Fix plugins with constructors
    public static final Plugin alKharidWarrior = new AlKharidWarriorNPC();
    public static final Plugin anton = new AntonNPC();
    public static final Plugin armorSuit = new ArmourSuitNPC();
    public static final Plugin barbarian = new BarbarianNPC(-1, null);
    public static final Plugin benny = new BennyNPC();
    public static final Plugin callisto = new CallistoNPC();
    public static final Plugin catablepon = new CatableponNPC(-1, null);
    public static final Plugin chaosElemental = new ChaosElementalNPC();
    public static final Plugin chromaticDragon = new ChromaticDragonNPC();
    public static final Plugin citizen = new CitizenNPC(-1, null);
    public static final Plugin cow = new CowNPC();
    public static final Plugin darkWizard = new DarkWizardNPC();
    public static final Plugin draynorTree = new DraynorTreeNPC(-1, null);
    public static final Plugin duck = new DuckNPC();
    public static final Plugin elementalWizard = new ElementalWizardNPC(-1, null);
    public static final Plugin fishing = new FishingNPC(-1, null);
    public static final Plugin giantMole = new GiantMoleNPC();
    //TODO: fix naming, sheesh.
    public static final Plugin goblinVillage = new GoblinVillageNPC();
    public static final Plugin guard = new GuardNPC(-1, null);
    public static final Plugin hans = new HansNPC();
    public static final Plugin highwayMan = new HighwayManNPC();
    public static final Plugin jogre = new JogreNPC();
    public static final Plugin kalphiteQueen = new KalphiteQueenNPC();
    public static final Plugin kamfreena = new KamfreenaNPC();
    public static final Plugin kingBlackDragon = new KingBlackDragonNPC();
    public static final Plugin lumberKitten = new LumberKittenNPC();
    public static final Plugin lumbridgeInstructor = new LumbridgeInstructorNPC();
    public static final Plugin man = new ManNPC();
    public static final Plugin metalDragon = new MetalDragonNPC();
    public static final Plugin monk = new MonkNPC();
    //TODO:Investigate NPC talk
    public static final Plugin npcTalk = new NPCTalkPlugin();
    public static final Plugin rat = new RatNPC(-1, null);
    public static final Plugin rockCrab = new RockCrabNPC();
    public static final Plugin shanomi = new ShanomiNPC();
    public static final Plugin sheep = new SheepNPC(-1, null);
    public static final Plugin shop = new ShopNPC(-1, null);
    public static final Plugin skippy = new SkippyNPC();
    public static final Plugin strayDog = new StrayDogNPC(-1, null);
    public static final Plugin throwerTroll = new ThrowerTrollNPC();
    public static final Plugin tribesman = new TribesmanNPC(-1, null);
    public static final Plugin tutorialChicken = new TutorialChickenNPC(-1, null);
    public static final Plugin tutorialRat = new TutorialRatNPC();
    //TODO: fix naming, sheesh.
    public static final Plugin vampireSlayer = new VampireSlayerNPC();
    public static final Plugin venenatis = new VenenatisNPC();
    public static final Plugin werewolf = new WerewolfNPC();

    /*
    Skills
     */

    public static final Plugin cooking = new CookingPlugin();
    public static final Plugin firemaking = new FiremakingPlugin();
    public static final Plugin fletching = new FletchingPlugin();

    //Herblore

    public static final Plugin barbarianMix = new BarbarianMixPlugin();
    public static final Plugin finishedPotion = new FinishedPotionPlugin();
    public static final Plugin grindItem = new GrindItemPlugin();
    public static final Plugin herbClean = new HerbCleanPlugin();
    public static final Plugin herbTar = new HerbTarPlugin();
    public static final Plugin makeSuperCombatPotion = new MakeSuperCombatPotionPlugin();
    public static final Plugin potionDecanting = new PotionDecantingPlugin();
    public static final Plugin unfinishedPotion = new UnfinishedPotionPlugin();

    //Magic

    public static final Plugin chargeOrb = new ChargeOrbSpells();
    public static final Plugin charge = new ChargeSpell();
    public static final Plugin enchantCrossbow = new EnchantCrossbowSpell();
    public static final Plugin modernTeleport = new ModernTeleportPlugin();
    public static final Plugin superheat = new SuperheatSpell();
    public static final Plugin teleother = new TeleotherSpells();

    public static final Plugin mining = new MiningMinePlugin();

    public static final Plugin prayer = new BoneBuryingOptionPlugin();

    //Runecrafting

    public static final Plugin abyssalNPC = new AbyssalNPC();
    public static final Plugin abyss = new AbyssPlugin();
    public static final Plugin darkMageDialogue = new DarkMageDialogue();
    public static final Plugin zamorakMageDialogue = new ZamorakMageDialogue();
    public static final Plugin combinationRune = new CombinationRunePlugin();
    public static final Plugin enchantTiara = new EnchantTiaraPlugin();
    public static final Plugin mysteriousRuin = new MysteriousRuinPlugin();
    public static final Plugin runecrafting = new RunecraftingPlugin();
    public static final Plugin runePouch = new RunePouchPlugin();
    public static final Plugin tiara = new TiaraPlugin();

    //Thieving

    public static final Plugin pickpocketNPC = new PickpocketNPCPlugin();

    //Woodcutting

    public static final Plugin chop = new ChopPlugin();

    /*
    Tutorial
     */

    public static final Plugin brotherBraceDialogue = new BrotherBraceDialogue();
    public static final Plugin combatInstructorDialogue = new CombatInstructorDialogue();
    public static final Plugin financialAdvisorDialogue = new FinancialAdvisorDialogue();
    public static final Plugin masterChefDialogue = new MasterChefDialogue();
    public static final Plugin miningInstructorDialogue = new MiningInstructorDialogue();
    public static final Plugin questGuideDialogue = new QuestGuideDialogue();
    public static final Plugin rsGuideDialogue = new RSGuideDialogue();
    public static final Plugin survivalExpertDialogue = new SurvivalExpertDialogue();
    public static final Plugin tutorialBook = new TutorialBook();
    public static final Plugin tutorialCompletionDialogue = new TutorialCompletionDialogue();
    public static final Plugin tutorialIsland = new TutorialIslandPlugin();


    public static void init() {
        declareSpecPlugins();
        declareSpellPlugins();
        declareDialoguePlugins();
        declareInteractionPlugins();
        declareNPCPlugins();
        declareSkillPlugins();
        declareTutorialPlugins();
    }

    public static void declareSpecPlugins() {
        combatPluginRepo.add(attackOption);
        combatPluginRepo.add(ancientMaceSpec);
        combatPluginRepo.add(energyDrainSpec);
        combatPluginRepo.add(excaliburSpec);
        combatPluginRepo.add(impaleSpec);
        combatPluginRepo.add(powershotSpec);
        combatPluginRepo.add(powerstabSpec);
        combatPluginRepo.add(punctureSpec);
        combatPluginRepo.add(quickSmashSpec);
        combatPluginRepo.add(rampageSpec);
        combatPluginRepo.add(saradominsLightningSpec);
        combatPluginRepo.add(seercullSpec);
        combatPluginRepo.add(severSpec);
        combatPluginRepo.add(snapshotSpec);
        combatPluginRepo.add(sweepSpec);
        combatPluginRepo.add(warstrikeSpec);
    }

    public static void declareSpellPlugins() {
        combatPluginRepo.add(airSpell);
        combatPluginRepo.add(bindSpell);
    }

    public static void declareDialoguePlugins() {
        dialoguePluginRepo.add(faridDialogue);
        dialoguePluginRepo.add(hansDialogue);
        dialoguePluginRepo.add(herbloreDialogue);
        dialoguePluginRepo.add(makeoverMageDialogue);
        dialoguePluginRepo.add(manDialogue);
    }

    public static void declareInteractionPlugins() {

        //Interfaces

        interactionPluginRepo.add(combatTab);
        interactionPluginRepo.add(equipment);
        interactionPluginRepo.add(magicBook);

        //Items

        interactionPluginRepo.add(birdNest);
        interactionPluginRepo.add(consumableOption);
        interactionPluginRepo.add(diangoItem);
        interactionPluginRepo.add(enchantSpell);
        interactionPluginRepo.add(mysteryBox);
        interactionPluginRepo.add(pickup);
        interactionPluginRepo.add(questCape);
        interactionPluginRepo.add(teleTabsOption);

        //Objects

        //interactionPluginRepo.add(dwarfCannonHandler);
        //interactionPluginRepo.add(dwarfCannonRevolution);
        interactionPluginRepo.add(bankBooth);
        interactionPluginRepo.add(doorManaging);
        interactionPluginRepo.add(grandExchangeShortcut);
        interactionPluginRepo.add(staircaseManaging);
        interactionPluginRepo.add(wildernessDitch);

        //Player

        interactionPluginRepo.add(followOption);

    }

    public static void declareNPCPlugins() {
        NPCPluginRepo.add(alKharidWarrior);
        NPCPluginRepo.add(anton);
        NPCPluginRepo.add(armorSuit);
        NPCPluginRepo.add(barbarian);
        NPCPluginRepo.add(benny);
        NPCPluginRepo.add(callisto);
        NPCPluginRepo.add(catablepon);
        NPCPluginRepo.add(chaosElemental);
        NPCPluginRepo.add(chromaticDragon);
        NPCPluginRepo.add(citizen);
        NPCPluginRepo.add(cow);
        NPCPluginRepo.add(darkWizard);
        NPCPluginRepo.add(draynorTree);
        NPCPluginRepo.add(duck);
        NPCPluginRepo.add(elementalWizard);
        NPCPluginRepo.add(fishing);
        NPCPluginRepo.add(giantMole);
        NPCPluginRepo.add(goblinVillage);
        NPCPluginRepo.add(guard);
        NPCPluginRepo.add(hans);
        NPCPluginRepo.add(highwayMan);
        NPCPluginRepo.add(jogre);
        NPCPluginRepo.add(kalphiteQueen);
        NPCPluginRepo.add(kamfreena);
        NPCPluginRepo.add(kingBlackDragon);
        NPCPluginRepo.add(lumberKitten);
        NPCPluginRepo.add(lumbridgeInstructor);
        NPCPluginRepo.add(man);
        NPCPluginRepo.add(metalDragon);
        NPCPluginRepo.add(monk);
        NPCPluginRepo.add(npcTalk);
        NPCPluginRepo.add(rat);
        NPCPluginRepo.add(rockCrab);
        NPCPluginRepo.add(shanomi);
        NPCPluginRepo.add(sheep);
        NPCPluginRepo.add(shop);
        NPCPluginRepo.add(skippy);
        NPCPluginRepo.add(strayDog);
        NPCPluginRepo.add(throwerTroll);
        NPCPluginRepo.add(tribesman);
        NPCPluginRepo.add(tutorialChicken);
        NPCPluginRepo.add(tutorialRat);
        NPCPluginRepo.add(vampireSlayer);
        NPCPluginRepo.add(venenatis);
        NPCPluginRepo.add(werewolf);
    }

    public static void declareSkillPlugins() {

        skillPluginRepo.add(cooking);
        skillPluginRepo.add(firemaking);
        skillPluginRepo.add(fletching);

        //Herblore
        skillPluginRepo.add(barbarianMix);
        skillPluginRepo.add(finishedPotion);
        skillPluginRepo.add(grindItem);
        skillPluginRepo.add(herbClean);
        skillPluginRepo.add(herbTar);
        skillPluginRepo.add(makeSuperCombatPotion);
        skillPluginRepo.add(potionDecanting);
        skillPluginRepo.add(unfinishedPotion);

        //Magic
        skillPluginRepo.add(chargeOrb);
        skillPluginRepo.add(charge);
        skillPluginRepo.add(enchantCrossbow);
        skillPluginRepo.add(modernTeleport);
        skillPluginRepo.add(superheat);
        skillPluginRepo.add(teleother);

        skillPluginRepo.add(mining);
        skillPluginRepo.add(prayer);

        //Runecrafting
        skillPluginRepo.add(abyssalNPC);
        skillPluginRepo.add(abyss);
        skillPluginRepo.add(darkMageDialogue);
        skillPluginRepo.add(zamorakMageDialogue);
        skillPluginRepo.add(combinationRune);
        skillPluginRepo.add(enchantTiara);
        skillPluginRepo.add(mysteriousRuin);
        skillPluginRepo.add(runecrafting);
        skillPluginRepo.add(runePouch);
        skillPluginRepo.add(tiara);

        //Thieving
        skillPluginRepo.add(pickpocketNPC);

        //Woodcutting
        skillPluginRepo.add(chop);
    }

    public static void declareTutorialPlugins() {
        tutorialPluginRepo.add(brotherBraceDialogue);
        tutorialPluginRepo.add(combatInstructorDialogue);
        tutorialPluginRepo.add(financialAdvisorDialogue);
        tutorialPluginRepo.add(masterChefDialogue);
        tutorialPluginRepo.add(miningInstructorDialogue);
        tutorialPluginRepo.add(questGuideDialogue);
        tutorialPluginRepo.add(rsGuideDialogue);
        tutorialPluginRepo.add(survivalExpertDialogue);
        tutorialPluginRepo.add(tutorialBook);
        tutorialPluginRepo.add(tutorialCompletionDialogue);
        tutorialPluginRepo.add(tutorialIsland);
    }
}
