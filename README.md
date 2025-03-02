# Coding Exercise
I have done the majority of my app development in flutter, so this was both similar and different in many ways. I used several online resources such as geeks for geeks 
to learn Retrofit and android.com to learn Jetpack Compose.

## Things to look for:
<ul>
  <li>I did a few things to show grouping by listId
  <ol>
    <li>I had each element say explicitly which group its in</li>
    <li>I color coded elements by groups</li>
    <li>I allowed the user to toggle visible groups</li>
  </ol>
  </li>
  <li>In order to sort elements by name after by group, I assumed that what was wanted was not a normal unicode sorting, but instead sorting them by the number in the name.
  <ul>
    <li>All names follow the format "Item #", so I just parsed the number</li>
    <li>It doesn't make sense that "Item 23" would be ordered after "Item 123"</li>
  </ul>
  </li>
  <li>
    Check out my javadocs and unit tests. It was important to me to keep my codebase both functional and understandable.
  </li>
</ul>

## Thank you!
