package de.ssherlock.global.transport;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a Checker DTO.
 *
 * @author Victor Vollmann
 */
public class Checker implements Serializable {

  /** Serial Version UID. */
  @Serial private static final long serialVersionUID = 1L;

  /** ID of the Checker. */
  private long id;

  /** Name of the Checker. */
  private String name;

  /**
   * for Compilation and Identity Checker null, for Spacing Checker the maximum line width, for User
   * Defined Checkers the input.
   */
  private String parameterOne;

  /**
   * for Compilation, Identity and Spacing Checker null, for User Defined Checkers the expected
   * output.
   */
  private String parameterTwo;

  /** Whether the Checker is mandatory for a submission. */
  private boolean mandatory;

  /** Whether the Checker is visible. */
  private boolean visible;

  /** The type of checker. */
  private CheckerType checkerType;

  /** The id of the exercise associated with this checker. */
  private long exerciseId;

  /** Default constructor. */
  public Checker() {}

  /**
   * Gets id.
   *
   * @return the id
   */
  public long getId() {
    return id;
  }

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets name.
   *
   * @param name the name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets parameter one.
   *
   * @return the parameter one
   */
  public String getParameterOne() {
    return parameterOne;
  }

  /**
   * Sets parameter one.
   *
   * @param parameterOne the parameter one
   */
  public void setParameterOne(String parameterOne) {
    this.parameterOne = parameterOne;
  }

  /**
   * Gets parameter two.
   *
   * @return the parameter two
   */
  public String getParameterTwo() {
    return parameterTwo;
  }

  /**
   * Sets parameter two.
   *
   * @param parameterTwo the parameter two
   */
  public void setParameterTwo(String parameterTwo) {
    this.parameterTwo = parameterTwo;
  }

  /**
   * Gets mandatory.
   *
   * @return the boolean
   */
  public boolean isMandatory() {
    return mandatory;
  }

  /**
   * Sets mandatory.
   *
   * @param mandatory the mandatory
   */
  public void setMandatory(boolean mandatory) {
    this.mandatory = mandatory;
  }

  /**
   * Gets visible.
   *
   * @return the boolean
   */
  public boolean isVisible() {
    return visible;
  }

  /**
   * Sets visible.
   *
   * @param visible the visible
   */
  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  /**
   * Gets checker type.
   *
   * @return the checker type
   */
  public CheckerType getCheckerType() {
    return checkerType;
  }

  /**
   * Sets checker type.
   *
   * @param checkerType the checker type
   */
  public void setCheckerType(CheckerType checkerType) {
    this.checkerType = checkerType;
  }

  /**
   * Gets exercise id.
   *
   * @return the exercise id
   */
  public long getExerciseId() {
    return exerciseId;
  }

  /**
   * Sets exercise id.
   *
   * @param exerciseId the exercise id
   */
  public void setExerciseId(long exerciseId) {
    this.exerciseId = exerciseId;
  }
}
