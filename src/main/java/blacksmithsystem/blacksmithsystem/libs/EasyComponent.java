package blacksmithsystem.blacksmithsystem.libs;

import com.google.common.base.Preconditions;
import net.md_5.bungee.api.chat.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class EasyComponent {
    private BaseComponent current;
    private final List<BaseComponent> parts = new ArrayList<>();

    public EasyComponent(EasyComponent original) {
        current = original.current.duplicate();

        for (final BaseComponent baseComponent : original.parts)
            parts.add(baseComponent.duplicate());
    }

    public EasyComponent(String text) {
        current = new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', text)));
    }

    public EasyComponent(BaseComponent component) {
        current = component.duplicate();
    }

    public EasyComponent append(BaseComponent component) {
        return append(component, ComponentBuilder.FormatRetention.ALL);
    }

    public EasyComponent append(BaseComponent component, ComponentBuilder.FormatRetention retention) {
        parts.add(current);

        final BaseComponent previous = current;
        current = component.duplicate();
        current.copyFormatting(previous, retention, false);
        return this;
    }

    public EasyComponent append(BaseComponent[] components) {
        return append(components, ComponentBuilder.FormatRetention.ALL);
    }

    public EasyComponent append(BaseComponent[] components, ComponentBuilder.FormatRetention retention) {
        Preconditions.checkArgument(components.length != 0, "No components to append");

        final BaseComponent previous = current;
        for (final BaseComponent component : components) {
            parts.add(current);

            current = component.duplicate();
            current.copyFormatting(previous, retention, false);
        }

        return this;
    }

    public EasyComponent append(String text) {
        return append(text, ComponentBuilder.FormatRetention.FORMATTING);
    }

    public EasyComponent append(String text, ComponentBuilder.FormatRetention retention) {
        parts.add(current);

        final BaseComponent old = current;
        current = new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', text)));
        current.copyFormatting(old, retention, false);

        return this;
    }

    public EasyComponent onClickRunCmd(String text) {
        return onClick(ClickEvent.Action.RUN_COMMAND, text);
    }

    public EasyComponent onClickSuggestCmd(String text) {
        return onClick(ClickEvent.Action.SUGGEST_COMMAND, text);
    }

    public EasyComponent onClick(ClickEvent.Action action, String text) {
        current.setClickEvent(new ClickEvent(action, ChatColor.translateAlternateColorCodes('&', text)));
        return this;
    }

    public EasyComponent onHover(String text) {
        return onHover(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, text);
    }

    public EasyComponent onHover(net.md_5.bungee.api.chat.HoverEvent.Action action, String text) {
        current.setHoverEvent(new HoverEvent(action, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', text))));

        return this;
    }

    public EasyComponent retain(ComponentBuilder.FormatRetention retention) {
        current.retain(retention);
        return this;
    }

    public BaseComponent[] create() {
        final BaseComponent[] result = parts.toArray(new BaseComponent[parts.size() + 1]);
        result[parts.size()] = current;

        return result;
    }

    public void send(Player... players) {
        final BaseComponent[] comp = create();

        for (final Player player : players)
            player.spigot().sendMessage(comp);
    }

    public static final EasyComponent builder(String text) {
        return new EasyComponent(text);
    }
}
