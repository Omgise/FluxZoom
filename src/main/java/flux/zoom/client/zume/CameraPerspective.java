package flux.zoom.client.zume;

/**
 * Matches Zume's CameraPerspective enum order so it can be indexed by
 * Minecraft's GameSettings.thirdPersonView (0=FIRST_PERSON, 1=THIRD_PERSON, 2=THIRD_PERSON_FRONT).
 */
public enum CameraPerspective {
    FIRST_PERSON,
    THIRD_PERSON,
    THIRD_PERSON_FRONT
}
