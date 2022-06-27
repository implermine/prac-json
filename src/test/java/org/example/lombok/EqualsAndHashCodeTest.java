package org.example.lombok;


public class EqualsAndHashCodeTest {

    private static class Person{
        private String name;
        private Long height;
        private int weight;
        private Gender gender;
        private Info info;

        /**
         * Delomboked EqualsAndHashCode
         */
        public boolean equals(final Object o) {

            // 동일성 조건 1 : 같은 객체
            if (o == this) return true;


            // 동등성 조건 1-1 : 같은 클래스 (상속), other는 this의 인스턴스이다, other는 this보다 작거나 같다.
            if (!(o instanceof Person)) return false;

            // 동등성 조건 1-2 : 같은 클래스 (상속), this는 other의 인스턴스이다, this는 other보다 작거나 같다.
            final Person other = (Person) o; // -> 다운캐스팅 시도
            if (!other.canEqual((Object) this)) return false;

            // 동등성을 평가하려면 서로 Polymorphism이 가능해야 한다.



            final Object this$name = this.name;
            final Object other$name = other.name;
            // 이쪽(this)가 null 인데, 저쪽(other)이 not null 이라면 동등성 실패
            // 이쪽(this)가 null 이 아니고, 저쪽(other)과 같다면 (당연 null check) 넘어감
            if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;

            final Object this$height = this.height;
            final Object other$height = other.height;
            if (this$height == null ? other$height != null : !this$height.equals(other$height)) return false;

            // primitive type에 대해선 null check를 하지 않는다.
            if (this.weight != other.weight) return false;

            final Object this$gender = this.gender;
            final Object other$gender = other.gender;
            if (this$gender == null ? other$gender != null : !this$gender.equals(other$gender)) return false;

            // (중요)
            // 보다싶이 해당 target 객체의 Overrided equals 메서드를 사용한다.
            // 따라서 제대로 동등성 비교를 수행하려면 nested objects에도 @EqualsAndHashCode를 붙이거나 구현해야한다.
            final Object this$info = this.info;
            final Object other$info = other.info;
            if (this$info == null ? other$info != null : !this$info.equals(other$info)) return false;

            return true;
        }

        // Object는 this 인스턴스
        protected boolean canEqual(final Object $this) {
            return $this instanceof Person;
        }


        /**
         * Delombked EqualsAndHashCode
         *
         * 모루겠음;; Equals에 늘 같은 Id를 공급하는정도로만 생각
         */
        public int hashCode() {
            final int PRIME = 59;
            int result = 1;

            final Object $name = this.name;
            result = result * PRIME + ($name == null ? 43 : $name.hashCode());

            final Object $height = this.height;
            result = result * PRIME + ($height == null ? 43 : $height.hashCode());
            result = result * PRIME + this.weight;

            final Object $gender = this.gender;
            result = result * PRIME + ($gender == null ? 43 : $gender.hashCode());

            final Object $info = this.info;
            result = result * PRIME + ($info == null ? 43 : $info.hashCode());

            return result;
        }
    }

    private static class Info{
        private String nickName;
    }

    private static enum Gender{
        MAN,
        WOMAN
    }
}
