package de.hhn.se.labswp.bugamap.responses;

/**
 * StandardResponse is a class that represents a basic response format that can be used by the API
 * endpoints to send responses back to the client.
 */
public class StandardResponse {

  /**
   * Indicates if the operation was successful or not.
   */
  private final boolean success;

  /**
   * Contains a message to describe the response.
   */
  private final String message;

  /**
   * Constructor for the StandardResponse class.
   *
   * @param success Indicates if the operation was successful or not.
   * @param message Contains a message to describe the response.
   */
  public StandardResponse(boolean success, String message) {
    this.success = success;
    this.message = message;
  }
}
