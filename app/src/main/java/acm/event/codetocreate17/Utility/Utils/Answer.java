package acm.event.codetocreate17.Utility.Utils;

import android.os.Parcel;
import android.os.Parcelable;

public class Answer implements Parcelable {

  private String name;


  public Answer(String name) {
    this.name = name;
  }

  protected Answer(Parcel in) {
    name = in.readString();
  }

  public String getName() {
    return name;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Answer)) return false;

    Answer answer = (Answer) o;


    return getName() != null ? getName().equals(answer.getName()) : answer.getName() == null;

  }

  @Override
  public int hashCode() {
    int result = getName() != null ? getName().hashCode() : 0;

    return result;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<Answer> CREATOR = new Creator<Answer>() {
    @Override
    public Answer createFromParcel(Parcel in) {
      return new Answer(in);
    }

    @Override
    public Answer[] newArray(int size) {
      return new Answer[size];
    }
  };
}

