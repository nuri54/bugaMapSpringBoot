package de.hhn.se.labswp.bugamap.responses;

/**
 * Represents a response for saving data to the database. Contains information about whether the
 * save operation was successful or not, and a message associated with it.
 */
public class DatabaseSaveResponse {

  /**
   * Whether the save operation was successful or not.
   */
  private boolean success;
  /**
   * A message associated with the save operation.
   */
  private String message;

  /**
   * Constructs a new {@code DatabaseSaveResponse} object.
   *
   * @param success Whether the save operation was successful or not.
   * @param message A message associated with the save operation.
   */
  public DatabaseSaveResponse(boolean success, String message) {
    this.success = success;
    this.message = message;
  }

  /**
   * Gets the value of the {@code success} field.
   *
   * @return The value of the {@code success} field.
   */
  public boolean isSuccess() {
    return success;
  }

  /**
   * Sets the value of the success field.
   *
   * @param success The new value of the success field.
   */
  public void setSuccess(boolean success) {
    this.success = success;
  }

  /**
   * Gets the value of the message field.
   *
   * @return The value of the message field.
   */
  public String getMessage() {
    return message;
  }

  /**
   * Sets the value of the message field.
   *
   * @param message The new value of the message field.
   */
  public void setMessage(String message) {
    this.message = message;
  }
}
