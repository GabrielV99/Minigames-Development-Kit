package ro.Gabriel.Listener.Listeners;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import ro.Gabriel.Listener.CustomListener;
import ro.Gabriel.Misc.ReflectionUtils;

import java.util.Map;
import java.util.UUID;

public class AsyncPlayerPreLoginListener {//extends CustomListener<AsyncPlayerPreLoginEvent> {
    /*@Override
    public void run(AsyncPlayerPreLoginEvent event) {
        try {

            PlayerJoinEvent e;
            MinecraftServer server = MinecraftServer.getServer();
            WorldServer worldServer = server.getWorldServer(0);
            GameProfile gameProfile = new GameProfile(event.getUniqueId(), event.getName());
            PlayerInteractManager playerInteractManager = new PlayerInteractManager(server.getWorld());
            EntityPlayer entityPlayer = new EntityPlayer(server, worldServer, gameProfile, playerInteractManager);
            //entityPlayer.getBukkitEntity().sendMessage("MESSSSSSSSSSSSSSSAAAAAAAAAAAAAAAAAAAAAAAJJJJJ!");
            CraftServer craftServer = ((CraftServer) Bukkit.getServer());
            DedicatedPlayerList DPL = craftServer.getHandle();

            ((Map<UUID, EntityPlayer>)ReflectionUtils.getValue(DPL, PlayerList.class, true, "j"))
                    .put(event.getUniqueId(), entityPlayer);
            //Bukkit.getPlayer(event.getUniqueId());
        } catch (Exception e) {
            e.printStackTrace();
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "player fail load data...");
        }
    }*/

    /*@Override
    @EventHandler
    public void run(AsyncPlayerPreLoginEvent event) {
        try {
            Bukkit.getPlayer(event.getUniqueId());

            System.out.println("NAME: " + event.getName());
            MinecraftServer server = MinecraftServer.getServer();
            WorldServer worldServer = server.getWorldServer(0);
            GameProfile gameProfile = new GameProfile(event.getUniqueId(), event.getName());
            PlayerInteractManager playerInteractManager = new PlayerInteractManager(server.getWorld());

            EntityPlayer entityPlayer = new EntityPlayer(server, worldServer, gameProfile, playerInteractManager);
            NetworkManager manager = new NetworkManager(EnumProtocolDirection.CLIENTBOUND);//entityPlayer.playerConnection.networkManager;

            CraftServer craftServer = ((CraftServer) Bukkit.getServer());
            //DedicatedPlayerList DPL = craftServer.getHandle();

            this.a(manager, entityPlayer);

            getMain().log("&4AsyncPlayerPreLoginEvent ... 1");
            // logica extragerii datelor din baza de date si crearii player-ului
            this.getMain().getUserRepository().registerEntity(event.getUniqueId());// for MKD
            getMain().log("&4AsyncPlayerPreLoginEvent ... 2");
            MinigamesDevelopmentKit.getMinigames().forEach(minigame -> {
                Repository<UUID, ?> repository = minigame.getUserRepository();
                if(repository != null) {
                    repository.registerEntity(event.getUniqueId());
                }
            });// for other minigames
            getMain().log("&4AsyncPlayerPreLoginEvent ... 3");
        } catch (Exception e) {
            getMain().log("&4AsyncPlayerPreLoginEvent ... 4");
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "player fail load data...");
            getMain().log("&4AsyncPlayerPreLoginEvent ... 5");
            e.printStackTrace();
        }
    }

    public void a(NetworkManager networkmanager, EntityPlayer entityplayer) throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, IllegalAccessException, NoSuchFieldException {

        MinecraftServer server = MinecraftServer.getServer();
        CraftServer craftServer = ((CraftServer) Bukkit.getServer());
        DedicatedPlayerList DPL = craftServer.getHandle();

        GameProfile gameprofile = entityplayer.getProfile();
        UserCache usercache = server.getUserCache();
        GameProfile gameprofile1 = usercache.a(gameprofile.getId());
        String s = gameprofile1 == null ? gameprofile.getName() : gameprofile1.getName();
        usercache.a(gameprofile);
        NBTTagCompound nbttagcompound = DPL.a(entityplayer);
        if (nbttagcompound != null && nbttagcompound.hasKey("bukkit")) {
            NBTTagCompound bukkit = nbttagcompound.getCompound("bukkit");
            s = bukkit.hasKeyOfType("lastKnownName", 8) ? bukkit.getString("lastKnownName") : s;
        }

        entityplayer.spawnIn(server.getWorldServer(entityplayer.dimension));
        entityplayer.playerInteractManager.a((WorldServer)entityplayer.world);
        String s1 = "local";
        if (networkmanager.getSocketAddress() != null) {
            s1 = networkmanager.getSocketAddress().toString();
        }

        Player bukkitPlayer = entityplayer.getBukkitEntity();
        PlayerSpawnLocationEvent ev = new PlayerSpawnLocationEvent(bukkitPlayer, bukkitPlayer.getLocation());
        Bukkit.getPluginManager().callEvent(ev);
        Location loc = ev.getSpawnLocation();
        WorldServer world = ((CraftWorld)loc.getWorld()).getHandle();
        entityplayer.spawnIn(world);
        entityplayer.setPosition(loc.getX(), loc.getY(), loc.getZ());
        //ReflectionUtils.invokeMethod(entityplayer, "Entity", ReflectionUtils.PackageType.MINECRAFT_SERVER, "setYawPitch", loc.getYaw(), loc.getPitch());//entityplayer.setYawPitch(loc.getYaw(), loc.getPitch());
        WorldServer worldserver = server.getWorldServer(entityplayer.dimension);
        WorldData worlddata = worldserver.getWorldData();
        //ReflectionUtils.invokeMethod(DPL, "PlayerList", ReflectionUtils.PackageType.MINECRAFT_SERVER, "a", entityplayer, (EntityPlayer)null, worldserver);//DPL.a(entityplayer, (EntityPlayer)null, worldserver);
        PlayerConnection playerconnection = new PlayerConnection(server, networkmanager, entityplayer);
        playerconnection.sendPacket(new PacketPlayOutLogin(entityplayer.getId(), entityplayer.playerInteractManager.getGameMode(), worlddata.isHardcore(), worldserver.worldProvider.getDimensionManager().getDimensionID(), worldserver.getDifficulty(), DPL.getMaxPlayers(), worlddata.getType(), worldserver.getGameRules().getBoolean("reducedDebugInfo")));
        entityplayer.getBukkitEntity().sendSupportedChannels();
        playerconnection.sendPacket(new PacketPlayOutCustomPayload("MC|Brand", (new PacketDataSerializer(Unpooled.buffer())).a(server.getServerModName())));
        playerconnection.sendPacket(new PacketPlayOutServerDifficulty(worlddata.getDifficulty(), worlddata.isDifficultyLocked()));
        playerconnection.sendPacket(new PacketPlayOutAbilities(entityplayer.abilities));
        playerconnection.sendPacket(new PacketPlayOutHeldItemSlot(entityplayer.inventory.itemInHandIndex));
        DPL.f(entityplayer);
        entityplayer.getStatisticManager().c();
        entityplayer.F().a(entityplayer);
        DPL.sendScoreboard((ScoreboardServer)worldserver.getScoreboard(), entityplayer);
        server.aD();
        String joinMessage;
        if (entityplayer.getName().equalsIgnoreCase(s)) {
            joinMessage = "§e" + LocaleI18n.a("multiplayer.player.joined", new Object[]{entityplayer.getName()});
        } else {
            joinMessage = "§e" + LocaleI18n.a("multiplayer.player.joined.renamed", new Object[]{entityplayer.getName(), s});
        }

        //DPL.onPlayerJoin(entityplayer, joinMessage);
        this.onPlayerJoin(DPL, server, craftServer, entityplayer, joinMessage);
        worldserver = server.getWorldServer(entityplayer.dimension);
        playerconnection.a(entityplayer.locX, entityplayer.locY, entityplayer.locZ, entityplayer.yaw, entityplayer.pitch);
        DPL.b(entityplayer, worldserver);
        if (!server.getResourcePack().isEmpty()) {
            entityplayer.setResourcePack(server.getResourcePack(), server.getResourcePackHash());
        }

        Iterator iterator = entityplayer.getEffects().iterator();

        while(iterator.hasNext()) {
            MobEffect mobeffect = (MobEffect)iterator.next();
            playerconnection.sendPacket(new PacketPlayOutEntityEffect(entityplayer.getId(), mobeffect));
        }

        if (nbttagcompound != null && nbttagcompound.hasKeyOfType("RootVehicle", 10)) {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("RootVehicle");
            Entity entity = ChunkRegionLoader.a(nbttagcompound1.getCompound("Entity"), worldserver, true);
            if (entity != null) {
                UUID uuid = nbttagcompound1.a("Attach");
                Iterator iterator1;
                Entity entity1;
                if (entity.getUniqueID().equals(uuid)) {
                    entityplayer.a(entity, true);
                } else {
                    iterator1 = entity.bG().iterator();

                    while(iterator1.hasNext()) {
                        entity1 = (Entity)iterator1.next();
                        if (entity1.getUniqueID().equals(uuid)) {
                            entityplayer.a(entity1, true);
                            break;
                        }
                    }
                }

                if (!entityplayer.isPassenger()) {
                    //DPL.f.warn("Couldn't reattach entity to player");
                    worldserver.removeEntity(entity);
                    iterator1 = entity.bG().iterator();

                    while(iterator1.hasNext()) {
                        entity1 = (Entity)iterator1.next();
                        worldserver.removeEntity(entity1);
                    }
                }
            }
        }

        entityplayer.syncInventory();
        //f.info(entityplayer.getName() + "[" + s1 + "] logged in with entity id " + entityplayer.getId() + " at ([" + entityplayer.world.worldData.getName() + "]" + entityplayer.locX + ", " + entityplayer.locY + ", " + entityplayer.locZ + ")");
    }

    public void onPlayerJoin(DedicatedPlayerList DPL, MinecraftServer server, CraftServer cserver,
                             EntityPlayer entityplayer, String joinMessage) {
        try {
            DPL.players.add(entityplayer);
            Map<String, EntityPlayer> playersByName = (Map<String, EntityPlayer>) ReflectionUtils.getValue(DPL, PlayerList.class, true, "playersByName");

            playersByName.put(entityplayer.getName(), entityplayer);
            ((Map<UUID, EntityPlayer>)ReflectionUtils.getValue(DPL, PlayerList.class, true, "j"))
                    .put(entityplayer.getUniqueID(), entityplayer);
            WorldServer worldserver = server.getWorldServer(entityplayer.dimension);
            PlayerJoinEvent playerJoinEvent = new PlayerJoinEvent(cserver.getPlayer(entityplayer), joinMessage);
            cserver.getPluginManager().callEvent(playerJoinEvent);
            if (entityplayer.playerConnection.networkManager.isConnected()) {
                joinMessage = playerJoinEvent.getJoinMessage();
                int i;
                if (joinMessage != null && joinMessage.length() > 0) {
                    IChatBaseComponent[] var8;
                    int var7 = (var8 = CraftChatMessage.fromString(joinMessage)).length;

                    for(i = 0; i < var7; ++i) {
                        IChatBaseComponent line = var8[i];
                        server.getPlayerList().sendAll(new PacketPlayOutChat(line));
                    }
                }

                ChunkIOExecutor.adjustPoolSize(DPL.getPlayerCount());
                PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, new EntityPlayer[]{entityplayer});

                for(i = 0; i < DPL.players.size(); ++i) {
                    EntityPlayer entityplayer1 = (EntityPlayer)DPL.players.get(i);
                    if (entityplayer1.getBukkitEntity().canSee(entityplayer.getBukkitEntity())) {
                        entityplayer1.playerConnection.sendPacket(packet);
                    }

                    if (entityplayer.getBukkitEntity().canSee(entityplayer1.getBukkitEntity())) {
                        entityplayer.playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, new EntityPlayer[]{entityplayer1}));
                    }
                }

                entityplayer.sentListPacket = true;

                DataWatcher dataWatcher = (DataWatcher) ReflectionUtils.getValue(entityplayer, Entity.class, true, "datawatcher");
                entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityMetadata(entityplayer.getId(), dataWatcher, true));
                if (entityplayer.world == worldserver && !worldserver.players.contains(entityplayer)) {
                    worldserver.addEntity(entityplayer);
                    DPL.a((EntityPlayer)entityplayer, (WorldServer)null);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}