package de.hhn.se.labswp.bugamap.responses;

import java.util.ArrayList;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a response for saving data to the database. Contains information about whether the
 * save operation was successful or not, and a message associated with it.
 */
@Getter
@Setter
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
   * List of failed objects.
   */
  private ArrayList<String> failed;

  /**
   * List of succeeded objects;
   */
  private ArrayList<String> succeeded;

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
}
