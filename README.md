# Concurrent Salad Preparation — Java

An academic **Systems Programming** assignment demonstrating coordination between multiple Java threads. Four workers share one `SlicerMachine`: three producers add vegetables and one consumer prepares salads once a complete set of ingredients is available.

## What the application does

Each salad requires **5 cucumbers, 3 tomatoes, and 1 onion**. The machine limits each ingredient's buffer to the amount needed for one salad. Producer threads wait when their buffer is full; the slicer waits when it has insufficient ingredients. Workers use Java intrinsic locks (`synchronized`) and condition waiting (`wait()` / `notifyAll()`) to coordinate safely.

This project is an educational concurrency simulation; it does not use a database, GUI, sockets or real equipment.

## Key files

| File | Responsibility |
| --- | --- |
| `src/assig3_3/SlicerMachine.java` | Shared counters, recipe requirements, synchronized buffer operations, and completion signaling |
| `src/assig3_3/CucumbersThread.java` | Produces cucumbers |
| `src/assig3_3/TomatoesThread.java` | Produces tomatoes |
| `src/assig3_3/OnionThread.java` | Produces onions |
| `src/assig3_3/SlicerThread.java` | Consumes a completed recipe and prepares one salad |
| `src/assig3_3/Main.java` | Validates input, launches workers, waits for completion and joins threads |

## Run

Requires a JDK (Java 8 or newer). From the repository root:

```bash
mkdir -p out
javac -d out src/assig3_3/*.java
java -cp out assig3_3.Main
```

Enter a non-negative integer when prompted. The output logs ingredient additions and each prepared salad, then reports the final count. Threads are stopped and joined at completion.

## Test

A lightweight shell test is included (requires Bash and a JDK):

```bash
bash tests/smoke_test.sh
```

The script checks requests for 0, 1, 3 and 10 salads and verifies that the number of `== preparing one more salad ==` messages matches the requested number.

## Design notes

- Each shared operation uses a synchronized instance method so counter changes are atomic with respect to other synchronized operations.
- Condition checks use `while` rather than `if`, which is important because `wait()` may return without the condition becoming true.
- `notifyAll()` awakens waiting workers after state changes; each worker rechecks its own condition.
- The main thread uses `awaitCompletion()` instead of busy-waiting and joins all workers before exiting.
- Invalid and negative inputs are rejected; zero salads is handled without starting workers.

## Academic context

Course: Systems Programming. This is a revised, documented version of the original classroom submission; changes include input validation and more orderly completion. The original assignment was developed collaboratively.
