package acm.event.codetocreate17.Model.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class FaqAnswerModel implements Parcelable {

  private String name;


  public FaqAnswerModel(String name) {
    this.name = name;
  }

  protected FaqAnswerModel(Parcel in) {
    name = in.readString();
  }

  public String getName() {
    return name;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof FaqAnswerModel)) return false;

    FaqAnswerModel answer = (FaqAnswerModel) o;


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

  public static final Creator<FaqAnswerModel> CREATOR = new Creator<FaqAnswerModel>() {
    @Override
    public FaqAnswerModel createFromParcel(Parcel in) {
      return new FaqAnswerModel(in);
    }

    @Override
    public FaqAnswerModel[] newArray(int size) {
      return new FaqAnswerModel[size];
    }
  };
}

