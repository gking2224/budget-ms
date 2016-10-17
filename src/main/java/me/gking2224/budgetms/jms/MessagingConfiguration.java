package me.gking2224.budgetms.jms;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import me.gking2224.common.jms.CommonMessagingConfiguration;

@Import({CommonMessagingConfiguration.class})
@ComponentScan("me.gking2224.budgetms.jms")
public class MessagingConfiguration {
}
