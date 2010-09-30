package foo

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 30, 2010
 * 
 */
class Person {
    @Lazy pets = ['Cat', 'Dog', 'Bird']
    @Lazy List lazyPets = { List list = new ArrayList(); list.add("Cat"); list.add("Dog"); list.add("Bird"); return list}()

}

def p = new Person()
assert !(p.dump().contains('Cat'))

assert p.pets.size() == 3
assert p.dump().contains('Cat')
assert p.lazyPets.size() == 3
assert p.lazyPets.get(0) == "Cat"