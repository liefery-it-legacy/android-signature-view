import android.os.Build;
import android.os.Parcel;
import com.liefery.android.signature_view.PathDescriptor;
import com.liefery.android.signature_view.actions.Action;
import com.liefery.android.signature_view.actions.LineToAction;
import com.liefery.android.signature_view.actions.MoveToAction;
import com.liefery.android.signature_view.actions.QuadToAction;
import org.junit.*;
import org.junit.runner.RunWith;
import org.robolectric.*;
import org.robolectric.annotation.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith( RobolectricTestRunner.class )
@Config( manifest = "src/main/AndroidManifest.xml", sdk = Build.VERSION_CODES.N )
public class RoboTest {

    @Test
    public void lineToAction_shouldBe_Parcelable() {
        LineToAction a1 = new LineToAction( 0, 0 );
        Parcel p1 = Parcel.obtain();
        a1.writeToParcel( p1, 0 );
        p1.setDataPosition( 0 );
        Action a2 = Action.CREATOR.createFromParcel( p1 );

        assertThat( a2 instanceof LineToAction ).isTrue();
        assertThat( a1.equals( a2 ) ).isTrue();
    }

    @Test
    public void moveToAction_shouldBe_Parcelable() {
        MoveToAction a1 = new MoveToAction( 0, 0 );
        Parcel p1 = Parcel.obtain();
        a1.writeToParcel( p1, 0 );
        p1.setDataPosition( 0 );
        Action a2 = Action.CREATOR.createFromParcel( p1 );

        assertThat( a2 instanceof MoveToAction ).isTrue();
        assertThat( a1.equals( a2 ) ).isTrue();
    }

    @Test
    public void quadToAction_shouldBe_Parcelable() {
        QuadToAction a1 = new QuadToAction( 10, 10, 10, 10 );
        Parcel p1 = Parcel.obtain();
        a1.writeToParcel( p1, 0 );
        p1.setDataPosition( 0 );
        Action a2 = Action.CREATOR.createFromParcel( p1 );

        assertThat( a2 instanceof QuadToAction ).isTrue();
        assertThat( a1.equals( a2 ) ).isTrue();
    }

    @Test
    public void pathDescriptor_shouldBe_Parcelable() {
        PathDescriptor p1 = new PathDescriptor();
        p1.moveTo( 1, 1 );
        p1.lineTo( 5, 5 );
        p1.quadTo( 10, 10, 10, 10 );

        Parcel parcel = Parcel.obtain();
        p1.writeToParcel( parcel, 0 );
        parcel.setDataPosition( 0 );

        PathDescriptor p2 = PathDescriptor.CREATOR.createFromParcel( parcel );

        assertThat( p1.equals( p2 ) ).isTrue();
    }

    @Test
    public void pathDescriptor_shouldBeEqual_withItself() {
        PathDescriptor p1 = new PathDescriptor();
        PathDescriptor p2 = new PathDescriptor();

        assertThat( p1.equals( p2 ) ).isTrue();

        p1.moveTo( 1, 1 );
        p2.moveTo( 1, 1 );

        assertThat( p1.equals( p2 ) ).isTrue();

        p1.lineTo( 5, 5 );
        p2.lineTo( 5, 5 );

        assertThat( p1.equals( p2 ) ).isTrue();

        p1.quadTo( 10, 10, 10, 10 );
        p2.quadTo( 10, 10, 10, 10 );

        assertThat( p1.equals( p2 ) ).isTrue();
    }

    @Test
    public void quadTo_shouldBeEqual() {
        QuadToAction q1 = new QuadToAction( 0, 0, 0, 0 );
        QuadToAction q2 = new QuadToAction( 0, 0, 0, 0 );
        QuadToAction q3 = new QuadToAction( 0, 0, 1, 0 );

        assertThat( q1.equals( q2 ) ).isTrue();
        assertThat( q1.equals( q3 ) ).isFalse();
        assertThat( q1.equals( new Object() ) ).isFalse();
    }

    @Test
    public void moveTo_shouldBeEqual() {
        MoveToAction m1 = new MoveToAction( 0, 0 );
        MoveToAction m2 = new MoveToAction( 0, 0 );
        MoveToAction m3 = new MoveToAction( 1, 0 );

        assertThat( m1.equals( m2 ) ).isTrue();
        assertThat( m1.equals( m3 ) ).isFalse();
        assertThat( m1.equals( new Object() ) ).isFalse();
    }

    @Test
    public void lineTo_shouldBeEqual_withItself() {
        LineToAction l1 = new LineToAction( 0, 0 );
        LineToAction l2 = new LineToAction( 0, 0 );
        LineToAction l3 = new LineToAction( 1, 0 );

        assertThat( l1.equals( l2 ) ).isTrue();
        assertThat( l1.equals( l3 ) ).isFalse();
        assertThat( l1.equals( new Object() ) ).isFalse();
    }

}