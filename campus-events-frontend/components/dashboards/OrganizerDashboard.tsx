"use client";

import { useEffect, useState } from "react";
import { getEvents, deleteEvent, createEvent } from "../../services/api";

export default function OrganizerDashboard() {
  const [events, setEvents] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [creating, setCreating] = useState(false);

  const [form, setForm] = useState({
    title: "",
    description: "",
    category: "",
    organizerName: "",
    location: "",
    startTime: "",
    endTime: "",
    isPublic: true,
  });

  useEffect(() => {
    fetchEvents();
  }, []);

  async function fetchEvents() {
    try {
      setLoading(true);
      const res = await getEvents();
      setEvents(res?.content || []);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  }

  async function handleCreate(e: any) {
    e.preventDefault();

    if (
      !form.title ||
      !form.category ||
      !form.organizerName ||
      !form.startTime ||
      !form.endTime
    ) {
      alert("Please fill required fields");
      return;
    }

    try {
      setCreating(true);

      // Convert datetime-local format properly
      const payload = {
        ...form,
        startTime: new Date(form.startTime).toISOString(),
        endTime: new Date(form.endTime).toISOString(),
      };

      await createEvent(payload);

      setForm({
        title: "",
        description: "",
        category: "",
        organizerName: "",
        location: "",
        startTime: "",
        endTime: "",
        isPublic: true,
      });

      await fetchEvents();
    } catch (err) {
      alert("Failed to create event");
    } finally {
      setCreating(false);
    }
  }

  async function deleteEventHandler(id: number) {
    await deleteEvent(id);
    fetchEvents();
  }

  return (
    <div className="p-8 text-white">
      <h2 className="text-3xl font-bold mb-6">Organizer Dashboard</h2>

      {/* CREATE EVENT FORM */}
      <div className="bg-slate-800 p-6 rounded-xl mb-8">
        <h3 className="text-xl font-semibold mb-4">
          Create New Event
        </h3>

        <form onSubmit={handleCreate} className="grid gap-4">

          <input
            placeholder="Title"
            className="p-2 bg-slate-700 rounded"
            value={form.title}
            onChange={(e) =>
              setForm({ ...form, title: e.target.value })
            }
          />

          <textarea
            placeholder="Description"
            className="p-2 bg-slate-700 rounded"
            value={form.description}
            onChange={(e) =>
              setForm({ ...form, description: e.target.value })
            }
          />

          <input
            placeholder="Category"
            className="p-2 bg-slate-700 rounded"
            value={form.category}
            onChange={(e) =>
              setForm({ ...form, category: e.target.value })
            }
          />

          <input
            placeholder="Organizer Name"
            className="p-2 bg-slate-700 rounded"
            value={form.organizerName}
            onChange={(e) =>
              setForm({ ...form, organizerName: e.target.value })
            }
          />

          <input
            placeholder="Location"
            className="p-2 bg-slate-700 rounded"
            value={form.location}
            onChange={(e) =>
              setForm({ ...form, location: e.target.value })
            }
          />

          <label>Start Time</label>
          <input
            type="datetime-local"
            className="p-2 bg-slate-700 rounded"
            value={form.startTime}
            onChange={(e) =>
              setForm({ ...form, startTime: e.target.value })
            }
          />

          <label>End Time</label>
          <input
            type="datetime-local"
            className="p-2 bg-slate-700 rounded"
            value={form.endTime}
            onChange={(e) =>
              setForm({ ...form, endTime: e.target.value })
            }
          />

          <label className="flex items-center gap-2">
            <input
              type="checkbox"
              checked={form.isPublic}
              onChange={(e) =>
                setForm({ ...form, isPublic: e.target.checked })
              }
            />
            Public Event
          </label>

          <button
            type="submit"
            disabled={creating}
            className="bg-green-600 px-4 py-2 rounded disabled:opacity-50"
          >
            {creating ? "Creating..." : "Create Event"}
          </button>
        </form>
      </div>

      {/* EVENT LIST */}
      {loading ? (
        <p>Loading events...</p>
      ) : events.length === 0 ? (
        <p>No events created yet.</p>
      ) : (
        <div className="grid gap-6">
          {events.map((event) => (
            <div
              key={event.id}
              className="bg-slate-800 p-6 rounded-xl"
            >
              <h3 className="text-xl font-semibold">
                {event.title}
              </h3>
              <p>Status: {event.status}</p>

              {event.status === "PENDING" && (
                <button
                  onClick={() => deleteEventHandler(event.id)}
                  className="mt-4 bg-red-600 px-4 py-2 rounded"
                >
                  Delete
                </button>
              )}
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
